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
        val allJavaDirs = project.rootProject.allprojects
            .map { it.file("src/main/java") }
            .filter { it.exists() }

        var deletedCount = 0
        allJavaDirs.forEach { javaDir ->
            deletedCount += deleteMatchedFiles(javaDir, allPrefixes)
            removeEmptyDirs(javaDir)
        }
        println("========================")
        println("共删除 $deletedCount 个文件，前缀范围：$allPrefixes")
        println("========================")
    }

    /**
     * 收集所有配置的前缀：全局 classPrefixName + moduleClassPrefixName 中的所有值
     */
    private fun collectAllPrefixes(): Set<String> {
        val prefixes = mutableSetOf<String>()
        configExtension.classPrefixName
            .filter { it.isNotBlank() }
            .forEach { prefixes.add(it) }
        configExtension.moduleClassPrefixName.values
            .flatten()
            .filter { it.isNotBlank() }
            .forEach { prefixes.add(it) }
        return prefixes
    }

    /**
     * 递归遍历目录，删除文件名（不含后缀）以指定前缀开头的 .java / .kt 文件
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
     * 递归删除空目录（从最深层向上清理）
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
