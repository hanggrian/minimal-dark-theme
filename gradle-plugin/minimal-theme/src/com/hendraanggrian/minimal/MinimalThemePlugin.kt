package com.hendraanggrian.minimal

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

/** Gradle plugin to create minimalistic webpage. */
open class MinimalThemePlugin : Plugin<Project> {
    companion object {
        const val PUBLISHING_GROUP = "publishing"
    }

    override fun apply(project: Project) {
        project.tasks.register<DeployPageTask>("deployPage") {
            group = PUBLISHING_GROUP
        }
    }
}
