package com.hendraanggrian.website.minimal

import com.hendraanggrian.website.minimal.internal.DefaultMinimal
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
        const val PUBLISHING_GROUP = "publishing"
        const val DEPLOY_RESOURCES_TASK = "deployResources"
        const val DEPLOY_WEBPAGES_TASK = "deployWebpages"
    }

    override fun apply(project: Project) {
        val hasApplicationPlugin = project.pluginManager.hasPlugin(ApplicationPlugin.APPLICATION_PLUGIN_NAME)
        val minimal = project.extensions.create(Minimal::class, "minimal", DefaultMinimal::class, project)

        val deployResources = project.tasks.register<DeployResourcesTask>(DEPLOY_RESOURCES_TASK) {
            group = PUBLISHING_GROUP
            description = "Write images, styles and scripts directories for minimal website."
        }
        val deployWebpages = project.tasks.register<DeployWebpagesTask>(DEPLOY_WEBPAGES_TASK) {
            group = PUBLISHING_GROUP
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
            minimal as DefaultMinimal
            deployResources {
                logo.set(minimal.logo)
                accentColor.set(minimal.accentColor)
                accentLightHoverColor.set(minimal.accentLightHoverColor)
                accentDarkHoverColor.set(minimal.accentDarkHoverColor)
                outputDirectory.set(minimal.outputDirectory)
                headerButtonsSize.set(minimal.headerButtons.get().size)
            }
            deployWebpages {
                authorName.set(minimal.authorName)
                authorUrl.set(minimal.authorUrl)
                projectName.set(minimal.projectName)
                projectDescription.set(minimal.projectDescription)
                projectUrl.set(minimal.projectUrl)
                footerCredit.set(minimal.footerCredit)
                markdownFile.set(minimal.markdownFile)
                outputDirectory.set(minimal.outputDirectory)
                headerButtons.set(minimal.headerButtons)
                webpageMap.set(minimal.webpageMap)
            }
        }
    }
}
