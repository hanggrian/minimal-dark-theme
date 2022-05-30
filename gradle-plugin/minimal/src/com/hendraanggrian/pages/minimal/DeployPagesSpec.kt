package com.hendraanggrian.pages.minimal

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property

interface DeployPagesSpec {

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
    val headerButtons: ListProperty<Minimal.HeaderButton>

    /**
     * Small theme credit in footer.
     * Enabled by default.
     */
    val footerCredit: Property<Boolean>

    /**
     * Markdown file which will be converted to `index.html` section.
     * If empty, the whole section is blank.
     */
    val markdownFile: RegularFileProperty

    /**
     * Additional custom webpages.
     * @see Minimal.webpage
     */
    val webpageMap: MapProperty<String, String>

    /**
     * Output directory of this task.
     * Default is `$projectDir/build/minimal`.
     */
    val outputDirectory: DirectoryProperty
}
