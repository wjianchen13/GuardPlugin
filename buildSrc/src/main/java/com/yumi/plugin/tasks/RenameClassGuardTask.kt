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
        // 收集所有模块的 src/main/java 目录
        val allJavaDirs = collectAllJavaDirs()
        // 收集所有有 src/main 的模块（用于 res / manifest 替换）
        val allAndroidProjects = collectAllAndroidProjects()
        // 遍历每个模块目录，执行重命名
        allJavaDirs.forEach { dir ->
            workDir(dir, allJavaDirs, allAndroidProjects)
        }
    }

    /**
     * 收集根项目下所有模块的 src/main/java 目录
     */
    private fun collectAllJavaDirs(): List<File> {
        return project.rootProject.allprojects
            .map { it.file("src/main/java") }
            .filter { it.exists() }
    }

    /**
     * 收集根项目下所有包含 src/main 的 Android 模块
     */
    private fun collectAllAndroidProjects(): List<Project> {
        return project.rootProject.allprojects
            .filter { it.file("src/main").exists() }
    }

    private fun workDir(file: File, allJavaDirs: List<File>, allAndroidProjects: List<Project>) {
        file.listFiles()?.forEach {
            if (it.isDirectory) {
                workDir(it, allJavaDirs, allAndroidProjects)
            } else {
                renameClass(it, allJavaDirs, allAndroidProjects)
            }
        }
    }

    private fun renameClass(file: File, allJavaDirs: List<File>, allAndroidProjects: List<Project>) {
        val path = file.path.replace(File.separator, ".").removeSuffix()
        val suffix = file.name.getSuffix()
        if (configExtension.filterSuffixFiles.contains(suffix)) {
            return
        }
        val javaPkg = "src.main.java."
        val kotlinPkg = "src.main.kotlin."
        var startIndex = path.lastIndexOf(javaPkg)
        var isJavaPkg = true
        if (startIndex == -1) {
            isJavaPkg = false
            startIndex = path.lastIndexOf(kotlinPkg)
            if (startIndex == -1) {
                throw IllegalArgumentException("Only src\\main\\java or src\\main\\kotlin directories are supported")
            }
        }
        val oldName = file.name.removeSuffix()
        if (oldName.isBlank()) {
            return
        }
        // 文件名本身是关键字，跳过重命名
        if (oldName.lowercase() in reservedKeywords) {
            return
        }
        val classPrefixNameArray = configExtension.classPrefixName.filter { it.isNotBlank() }.toTypedArray()
        if (classPrefixNameArray.isEmpty()) {
            throw IllegalArgumentException("The classPrefixName has not been configured yet. Please configure the classPrefixName before running the task")
        }
        val classPrefixName = if (classPrefixNameArray.size == 1) {
            classPrefixNameArray[0]
        } else {
            classPrefixNameArray[random.nextInt(classPrefixNameArray.size)]
        }
        val newName = "$classPrefixName${oldName}"
        val length = if (isJavaPkg) javaPkg.length else kotlinPkg.length
        val oldClassPath = path.substring(startIndex + length, path.length)
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
