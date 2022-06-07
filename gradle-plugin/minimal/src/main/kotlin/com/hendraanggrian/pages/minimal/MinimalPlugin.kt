package com.hendraanggrian.pages.minimal

import com.hendraanggrian.pages.minimal.internal.DefaultMinimal
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ApplicationPlugin
import org.gradle.api.plugins.JavaApplication
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.register

/** Gradle plugin to create minimalistic webpage. */
open class MinimalPlugin : Plugin<Project> {
    companion object {
        const val GROUP = "publishing"
        const val TASK_DEPLOY_RESOURCES = "deployResources"
        const val TASK_DEPLOY_PAGES = "deployPages"
    }

    override fun apply(project: Project) {
        val hasApplicationPlugin = project.pluginManager.hasPlugin(ApplicationPlugin.APPLICATION_PLUGIN_NAME)
        val minimal = project.extensions.create(Minimal::class, "minimal", DefaultMinimal::class, project)

        val deployResources = project.tasks.register<DeployResourcesTask>(TASK_DEPLOY_RESOURCES) {
            group = GROUP
            description = "Write images, styles and scripts directories for minimal website."
        }
        val deployWebpages = project.tasks.register<DeployPagesTask>(TASK_DEPLOY_PAGES) {
            group = GROUP
            description = "Write HTML files for minimal website."
            dependsOn(deployResources)
        }

        project.afterEvaluate {
            if (hasApplicationPlugin) {
                minimal.projectName.convention(
                    project.extensions.getByName<JavaApplication>(ApplicationPlugin.APPLICATION_PLUGIN_NAME)
                        .applicationName
                )
            }
            deployResources {
                accentColor.convention(minimal.accentColor)
                accentLightHoverColor.convention(minimal.accentLightHoverColor)
                accentDarkHoverColor.convention(minimal.accentDarkHoverColor)
                outputDirectory.convention(minimal.outputDirectory)
                headerButtonsSize.convention(minimal.headerButtons.get().size)
            }
            deployWebpages {
                icon.convention(minimal.icon)
                authorName.convention(minimal.authorName)
                authorUrl.convention(minimal.authorUrl)
                projectName.convention(minimal.projectName)
                projectDescription.convention(minimal.projectDescription)
                projectUrl.convention(minimal.projectUrl)
                headerButtons.convention(minimal.headerButtons)
                footerCredit.convention(minimal.footerCredit)
                pagesMap.convention(minimal.pagesMap)
                outputDirectory.convention(minimal.outputDirectory)
            }
        }
    }
}
