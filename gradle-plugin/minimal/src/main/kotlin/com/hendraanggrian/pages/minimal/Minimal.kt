package com.hendraanggrian.pages.minimal

import org.gradle.api.Action
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import java.io.Serializable

interface Minimal : ResourcesDeployment, PagesDeployment {
    /** Configures header buttons. Header buttons size is capped at 3. */
    fun headerButtons(action: Action<HeaderButtonsScope>)

    /** Configures webpages that will be generated. */
    fun pages(action: Action<PagesScope>)
}

interface ResourcesDeployment {
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

interface PagesDeployment {
    /**
     * Optional relative path to website logo.
     * E.g. `images/icon.png`.
     */
    val icon: Property<String>

    /**
     * Author full name in title and footer.
     * If left empty, corresponding tag in footer is removed but title will still show project name.
     */
    val authorName: Property<String>

    /**
     * Author website url in footer.
     * If left empty, author information in footer will not be clickable.
     */
    val authorUrl: Property<String>

    /**
     * Project full name in header.
     * If left empty, module name will be used.
     */
    val projectName: Property<String>

    /**
     * Project description in header.
     * If left empty, corresponding tag in header is removed.
     */
    val projectDescription: Property<String>

    /**
     * Project website url in header.
     * If left empty, corresponding tag in header is removed.
     */
    val projectUrl: Property<String>

    /**
     * Side-by-side header buttons, capped at 3.
     * @see Minimal.headerButtons
     */
    val headerButtons: ListProperty<HeaderButton>

    /**
     * Small theme credit in footer.
     * Enabled by default.
     */
    val footerCredit: Property<Boolean>

    /**
     * Webpages mapping.
     * @see Minimal.pages
     */
    val pagesMap: MapProperty<String, String>

    /**
     * Output directory of this task.
     * Default is `$projectDir/build/minimal`.
     */
    val outputDirectory: DirectoryProperty
}

/**
 * Header button data class.
 * @param line1 first line of text.
 * @param line2 second line of text, with stronger emphasis.
 * @param url absolute or relative path to redirect to on button click.
 */
data class HeaderButton(val line1: String, val line2: String, val url: String) : Serializable
