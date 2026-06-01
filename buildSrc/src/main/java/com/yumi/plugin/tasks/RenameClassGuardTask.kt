package com.yumi.plugin.tasks

import com.yumi.plugin.entension.*
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.util.*
import javax.inject.Inject

/**
 *   █████▒█    ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝
 *  ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝
 *  ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 *  ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 *           ░     ░ ░      ░  ░
 * @author : Leo
 * @date : 2022/12/16 19:57
 * @desc :
 * @since : xinxiniscool@gmail.com
 */
open class RenameClassGuardTask @Inject constructor(
    private val configExtension: ConfigExtension,
) : DefaultTask() {

    init {
        group = "guard"
    }

    private val random by lazy { Random() }

    // Kotlin 及 Java 关键字黑名单，禁止被当作类名重命名
    private val reservedKeywords = setOf(
        // Kotlin 硬关键字
        "as", "break", "class", "continue", "do", "else", "false", "for",
        "fun", "if", "in", "interface", "is", "null", "object", "package",
        "return", "super", "this", "throw", "true", "try", "typealias",
        "typeof", "val", "var", "when", "while",
        // Kotlin 软关键字 / 修饰符关键字
        "abstract", "actual", "annotation", "by", "catch", "companion",
        "const", "constructor", "crossinline", "data", "delegate",
        "dynamic", "enum", "expect", "external", "field", "file",
        "final", "finally", "get", "import", "init", "inline", "inner",
        "infix", "it", "lateinit", "noinline", "open", "operator",
        "out", "override", "param", "private", "property", "protected",
        "public", "receiver", "reified", "sealed", "set", "setparam",
        "suspend", "tailrec", "vararg", "where",
        // Java 关键字
        "boolean", "byte", "char", "double", "extends", "float",
        "implements", "import", "instanceof", "int", "long", "native",
        "new", "short", "static", "strictfp", "synchronized", "throws",
        "transient", "void", "volatile"
    )

    @TaskAction
    fun execute() {
        // 收集所有模块的 Java 源码目录（含 flavor 等自定义 sourceSet 目录）
        val allJavaDirs = collectAllJavaDirs()
        // 收集所有有 src/main 的模块（用于 res / manifest 替换）
        val allAndroidProjects = collectAllAndroidProjects()
        // 为每个模块目录构建前缀映射：javaDir -> 该模块对应的前缀数组
        val prefixMap = buildPrefixMap(allJavaDirs)
        // 遍历每个模块目录，执行重命名（传入 baseDir 供路径计算用）
        allJavaDirs.forEach { dir ->
            val prefixArray = prefixMap[dir] ?: return@forEach
            workDir(dir, dir, allJavaDirs, allAndroidProjects, prefixArray)
        }
    }

    /**
     * 为每个 javaDir 确定前缀数组：
     * 优先使用 moduleClassPrefixName 中该模块名对应的配置，
     * 未配置则回退到全局 classPrefixName
     */
    private fun buildPrefixMap(allJavaDirs: List<File>): Map<File, Array<String>> {
        val allProjects = project.rootProject.allprojects.toList()
        return allJavaDirs.associateWith { javaDir ->
            // 通过 projectDir 前缀匹配，找到最具体的宿主模块（不依赖固定层数）
            val moduleName = allProjects
                .filter { proj ->
                    javaDir.absolutePath.startsWith(proj.projectDir.absolutePath + File.separator)
                }
                .minByOrNull { proj ->
                    javaDir.absolutePath.length - proj.projectDir.absolutePath.length
                }
                ?.name
                ?: javaDir.parentFile.parentFile.parentFile.name  // 兜底：原来的 3 层逻辑
            val modulePrefix = configExtension.moduleClassPrefixName[moduleName]
                ?.filter { it.isNotBlank() }
                ?.toTypedArray()
            if (!modulePrefix.isNullOrEmpty()) {
                modulePrefix
            } else {
                configExtension.classPrefixName.filter { it.isNotBlank() }.toTypedArray()
            }
        }
    }

    /**
     * 收集根项目下所有模块的 Java 源码目录，包括 flavor 等自定义 sourceSet 目录。
     * 通过纯反射访问 Android 扩展，避免 buildSrc compileOnly 与运行时 AGP 版本不同
     * 导致的 ClassLoader 隔离问题（直接强转会静默返回 null）。
     */
    private fun collectAllJavaDirs(): List<File> {
        val dirs = mutableListOf<File>()
        project.rootProject.allprojects.forEach { proj ->
            val androidExt = proj.extensions.findByName("android")
            if (androidExt == null) {
                // 非 Android 模块：兜底只取 src/main/java
                proj.file("src/main/java").takeIf { it.exists() }?.let { dirs.add(it) }
                return@forEach
            }
            try {
                // 用反射调用 getSourceSets()，绕开 ClassLoader 隔离
                val sourceSetsContainer = androidExt.javaClass
                    .getMethod("getSourceSets").invoke(androidExt) as? Iterable<*>
                    ?: return@forEach
                for (ss in sourceSetsContainer) {
                    ss ?: continue
                    val ssName = ss.javaClass.getMethod("getName").invoke(ss) as? String ?: continue
                    if (ssName.startsWith("test") || ssName.startsWith("androidTest")) continue
                    // getJava() → AndroidSourceDirectorySet
                    val javaObj = ss.javaClass.getMethod("getJava").invoke(ss) ?: continue
                    // getSrcDirs() → Set<File>
                    val srcDirsRaw = javaObj.javaClass.getMethod("getSrcDirs").invoke(javaObj)
                        as? Iterable<*> ?: continue
                    srcDirsRaw.filterIsInstance<File>()
                        .filter { it.exists() && !it.absolutePath.contains("${File.separator}build${File.separator}") }
                        .forEach { dir ->
                            if (dirs.none { it.absolutePath == dir.absolutePath }) dirs.add(dir)
                        }
                }
            } catch (e: Exception) {
                // 反射失败时兜底
                proj.file("src/main/java").takeIf { it.exists() }?.let { dirs.add(it) }
            }
        }
        return dirs
    }

    /**
     * 收集根项目下所有包含 src/main 的 Android 模块
     */
    private fun collectAllAndroidProjects(): List<Project> {
        return project.rootProject.allprojects
            .filter { it.file("src/main").exists() }
    }

    private fun workDir(file: File, baseDir: File, allJavaDirs: List<File>, allAndroidProjects: List<Project>, prefixArray: Array<String>) {
        file.listFiles()?.forEach {
            if (it.isDirectory) {
                workDir(it, baseDir, allJavaDirs, allAndroidProjects, prefixArray)
            } else {
                renameClass(it, baseDir, allJavaDirs, allAndroidProjects, prefixArray)
            }
        }
    }

    private fun renameClass(file: File, baseDir: File, allJavaDirs: List<File>, allAndroidProjects: List<Project>, prefixArray: Array<String>) {
        val suffix = file.name.getSuffix()
        if (configExtension.filterSuffixFiles.contains(suffix)) {
            return
        }
        val oldName = file.name.removeSuffix()
        if (oldName.isBlank()) {
            return
        }
        // 文件名本身是关键字，跳过重命名
        if (oldName.lowercase() in reservedKeywords) {
            return
        }
        if (prefixArray.isEmpty()) {
            throw IllegalArgumentException("The classPrefixName has not been configured yet. Please configure the classPrefixName before running the task")
        }
        val classPrefixName = if (prefixArray.size == 1) {
            prefixArray[0]
        } else {
            prefixArray[random.nextInt(prefixArray.size)]
        }
        val newName = "$classPrefixName${oldName}"
        // 用 baseDir 直接计算相对类路径，支持任意源码目录结构（不再依赖 src.main.java 硬编码）
        val baseDirDots = baseDir.absolutePath.replace(File.separator, ".") + "."
        val fileDots = file.absolutePath.replace(File.separator, ".").removeSuffix()
        val baseDirIndex = fileDots.indexOf(baseDirDots)
        if (baseDirIndex == -1) return   // 文件不在 baseDir 下，跳过（防御性检查）
        val oldClassPath = fileDots.substring(baseDirIndex + baseDirDots.length)
        val oldClassDir = oldClassPath.getDirPath()
        val newClassPath = "${oldClassDir}.${newName}"

        // 1. 重命名文件
        file.renameTo(File(file.absolutePath.replace(oldName, newName)))

        // 2. 替换所有模块源码中的引用（跨模块）
        allJavaDirs.forEach { javaDir ->
            obfuscateAllClass(javaDir, oldClassPath, newClassPath, oldName, newName)
        }

        // 3. 替换所有模块资源文件中的引用（layout / navigation / AndroidManifest）
        allAndroidProjects.forEach { proj ->
            obfuscateRes(proj, oldClassPath, newClassPath, oldName)
        }
    }

    /**
     * 替换指定模块的资源文件引用
     */
    private fun obfuscateRes(
        proj: Project,
        oldClassPath: String,
        newClassPath: String,
        oldName: String
    ) {
        val resDir = proj.resDir()
        if (!resDir.exists()) return
        val listFiles = resDir.listFiles { _, name ->
            name.startsWith("layout") || name.startsWith("navigation")
        }?.toMutableList() ?: mutableListOf()

        val manifestFile = proj.manifestFile()
        if (manifestFile.exists()) listFiles.add(manifestFile)
        if (listFiles.isEmpty()) return

        proj.files(listFiles).asFileTree.forEach { xmlFile ->
            val parentName = xmlFile.parentFile.name
            when {
                parentName.startsWith("navigation") || parentName.startsWith("layout") -> {
                    val original = xmlFile.readText()
                    val replaced = original.replaceWords(oldClassPath, newClassPath)
                    if (original != replaced) xmlFile.writeText(replaced)
                }
                xmlFile.name == "AndroidManifest.xml" -> {
                    val xmlContent = mutableListOf<String>()
                    val original = xmlFile.readText()
                    var text = original
                    val namespace = runCatching {
                        proj.extensions.getByName("android")
                            .javaClass.getMethod("getNamespace")
                            .invoke(proj.extensions.getByName("android")) as? String
                    }.getOrNull()
                    findClassByManifest(text, xmlContent, namespace)
                    for (classPath in xmlContent) {
                        val className = classPath.getClassName()
                        if (className == oldName) {
                            text = text.replaceWords(classPath, newClassPath)
                        }
                    }
                    if (original != text) xmlFile.writeText(text)
                }
            }
        }
    }

    private fun obfuscateAllClass(
        file: File, oldClassPath: String,
        newClassPath: String, oldName: String, newName: String
    ) {
        file.listFiles()?.forEach {
            if (it.isDirectory) {
                obfuscateAllClass(it, oldClassPath, newClassPath, oldName, newName)
            } else {
                replaceClassText(it, oldClassPath, newClassPath, oldName, newName)
            }
        }
    }

    private fun replaceClassText(
        file: File, oldClassPath: String,
        newClassPath: String, oldName: String, newName: String
    ) {
        // oldName 是关键字时跳过
        if (oldName.lowercase() in reservedKeywords) return
        val originalContent = file.readText()
        // 检测原始行尾符，保留 CRLF 或 LF
        val lineSeparator = if (originalContent.contains("\r\n")) "\r\n" else "\n"
        val newContent = originalContent.split(lineSeparator).joinToString(lineSeparator) { line ->
            if (line.startsWith("import")) {
                line.replaceWords(oldClassPath, newClassPath)
            } else {
                line.replaceWords(oldName, newName)
            }
        }
        // 内容没有变化则不写入，避免无意义的行尾符差异触发 git 变更
        if (originalContent != newContent) {
            file.writeText(newContent)
        }
    }
}
