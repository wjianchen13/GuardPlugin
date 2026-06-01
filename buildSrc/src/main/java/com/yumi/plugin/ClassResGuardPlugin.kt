package com.yumi.plugin

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import com.yumi.plugin.entension.ConfigExtension
import com.yumi.plugin.tasks.RenameResGuardTask
import com.yumi.plugin.tasks.RenameClassGuardTask
import com.yumi.plugin.tasks.AddJunkFileGuardTask
import com.yumi.plugin.tasks.DeletePrefixClassGuardTask
import com.yumi.plugin.tasks.RenameDirGuardTask

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
 * @date : 2022/12/16 18:56
 * @desc :
 * @since : xinxiniscool@gmail.com
 */
class ClassResGuardPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        checkApplicationPlugin(project)
        val configExtension =
            project.extensions.create("classResGuard", ConfigExtension::class.java)
        project.tasks.create(
            "renameRes", RenameResGuardTask::class.java, configExtension
        )
        project.tasks.create("addJunkFile", AddJunkFileGuardTask::class.java, configExtension)
        project.tasks.create("renameClass", RenameClassGuardTask::class.java, configExtension)
        project.tasks.create("renameDir", RenameDirGuardTask::class.java, configExtension)
        project.tasks.create("deletePrefixClass", DeletePrefixClassGuardTask::class.java, configExtension)
    }


    private fun checkApplicationPlugin(project: Project) {
        val isApplication = project.plugins.hasPlugin("com.android.application")
        val isLibrary = project.plugins.hasPlugin("com.android.library")
        if (!isApplication && !isLibrary) {
            throw GradleException("Android Application or Library plugin required")
        }
    }
}