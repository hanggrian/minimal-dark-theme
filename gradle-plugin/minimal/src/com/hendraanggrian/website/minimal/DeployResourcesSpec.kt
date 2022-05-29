package com.hendraanggrian.website.minimal

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property

interface DeployResourcesSpec {

    /** Optional website logo. */
    val logo: Property<String>

    /**
     * Accent color of the webpage.
     * Default is material color `Blue A200`.
     */
    val accentColor: Property<String>

    /**
     * Accent color of the webpage when hovered in light theme.
     * Default is material color `Blue A200 Dark`.
     */
    val accentLightHoverColor: Property<String>

    /**
     * Accent color of the webpage when hovered in dark theme.
     * Default is material color `Blue A200 Light`.
     */
    val accentDarkHoverColor: Property<String>

    /**
     * Output directory of this task.
     * Default is `$projectDir/build/minimal`.
     */
    val outputDirectory: DirectoryProperty
}
