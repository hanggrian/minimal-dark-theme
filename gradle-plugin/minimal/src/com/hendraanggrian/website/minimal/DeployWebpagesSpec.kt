package com.hendraanggrian.website.minimal

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property

interface DeployWebpagesSpec {

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
     * Small theme credit in footer.
     * Enabled by default.
     */
    val footerCredit: Property<Boolean>

    /**
     * Markdown file which will be converted to section.
     * If empty, the whole section is blank.
     */
    val markdownFile: RegularFileProperty

    /**
     * Output directory of this task.
     * Default is `$projectDir/build/minimal`.
     */
    val outputDirectory: DirectoryProperty
}
