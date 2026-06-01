package com.yumi.plugin.tasks

import com.yumi.plugin.entension.ConfigExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.inject.Inject

open class DeletePrefixClassGuardTask @Inject constructor(
    private val configExtension: ConfigExtension,
) : DefaultTask() {

    init {
        group = "guard"
    }

    @TaskAction
    fun execute() {
        val allPrefixes = collectAllPrefixes()
        if (allPrefixes.isEmpty()) {
            println("未配置任何前缀，跳过删除")
            return
        }
        val allJavaDirs = collectAllJavaDirs()

        var deletedCount = 0
        allJavaDirs.forEach { javaDir ->
            deletedCount += deleteMatchedFiles(javaDir, allPrefixes)
            removeEmptyDirs(javaDir)
        }
        println("========================")
        println("共删除 $deletedCount 个文件，匹配前缀：$allPrefixes")
        println("========================")
    }

    /**
     * 收集所有需要匹配的前缀集合：
     *   - classPrefixName（全局前缀，支持多个）
     *   - moduleClassPrefixName 所有模块的所有候选前缀
     * 注意：中缀和后缀不作为文件名起始匹配，仅前缀用于 startsWith 判断。
     */
    private fun collectAllPrefixes(): Set<String> {
        val prefixes = mutableSetOf<String>()

        // 全局前缀
        configExtension.classPrefixName
            .filter { it.isNotBlank() }
            .forEach { prefixes.add(it) }

        // 各模块独立前缀
        configExtension.moduleClassPrefixName.values
            .flatten()
            .filter { it.isNotBlank() }
            .forEach { prefixes.add(it) }

        return prefixes
    }

    /**
     * 通过反射读取 Android 扩展的所有非测试 sourceSet 的 java.srcDirs，
     * 与 RenameClassGuardTask 保持一致，避免漏扫 flavor 等自定义目录。
     */
    private fun collectAllJavaDirs(): List<File> {
        val dirs = mutableListOf<File>()
        project.rootProject.allprojects.forEach { proj ->
            val androidExt = proj.extensions.findByName("android")
            if (androidExt == null) {
                proj.file("src/main/java").takeIf { it.exists() }?.let { dirs.add(it) }
                return@forEach
            }
            try {
                val sourceSetsContainer = androidExt.javaClass
                    .getMethod("getSourceSets").invoke(androidExt) as? Iterable<*>
                    ?: return@forEach
                for (ss in sourceSetsContainer) {
                    ss ?: continue
                    val ssName = ss.javaClass.getMethod("getName").invoke(ss) as? String ?: continue
                    if (ssName.startsWith("test") || ssName.startsWith("androidTest")) continue
                    val javaObj = ss.javaClass.getMethod("getJava").invoke(ss) ?: continue
                    val srcDirsRaw = javaObj.javaClass.getMethod("getSrcDirs").invoke(javaObj)
                        as? Iterable<*> ?: continue
                    srcDirsRaw.filterIsInstance<File>()
                        .filter { it.exists() && !it.absolutePath.contains("${File.separator}build${File.separator}") }
                        .forEach { dir ->
                            if (dirs.none { it.absolutePath == dir.absolutePath }) dirs.add(dir)
                        }
                }
            } catch (e: Exception) {
                proj.file("src/main/java").takeIf { it.exists() }?.let { dirs.add(it) }
            }
        }
        return dirs
    }

    /**
     * 递归遍历目录，删除文件名（不含扩展名）以任意配置前缀开头的 .java / .kt 文件。
     */
    private fun deleteMatchedFiles(dir: File, prefixes: Set<String>): Int {
        var count = 0
        dir.listFiles()?.forEach { file ->
            if (file.isDirectory) {
                count += deleteMatchedFiles(file, prefixes)
            } else if (file.extension == "java" || file.extension == "kt") {
                val fileName = file.nameWithoutExtension
                if (prefixes.any { prefix -> fileName.startsWith(prefix) }) {
                    println("删除：${file.absolutePath}")
                    file.delete()
                    count++
                }
            }
        }
        return count
    }

    /**
     * 递归删除空目录（从最深层向上清理）。
     */
    private fun removeEmptyDirs(dir: File) {
        dir.listFiles()?.forEach { child ->
            if (child.isDirectory) removeEmptyDirs(child)
        }
        if (dir.isDirectory && dir.listFiles().isNullOrEmpty()) {
            dir.delete()
        }
    }
}
