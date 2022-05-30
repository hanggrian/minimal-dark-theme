package com.hendraanggrian.pages.minimal

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.mapProperty
import org.gradle.kotlin.dsl.property

/** Task to run when `deployWebpages` command is executed. */
open class DeployPagesTask : DefaultTask(), DeployPagesSpec {

    @Optional
    @Input
    override val icon: Property<String> = project.objects.property()

    @Optional
    @Input
    override val authorName: Property<String> = project.objects.property()

    @Optional
    @Input
    override val authorUrl: Property<String> = project.objects.property()

    @Input
    override val projectName: Property<String> = project.objects.property()

    @Optional
    @Input
    override val projectDescription: Property<String> = project.objects.property()

    @Optional
    @Input
    override val projectUrl: Property<String> = project.objects.property()

    @Input
    override val headerButtons: ListProperty<Minimal.HeaderButton> = project.objects.listProperty()

    @Input
    override val footerCredit: Property<Boolean> = project.objects.property()

    @Optional
    @InputFile
    override val markdownFile: RegularFileProperty = project.objects.fileProperty()

    @Input
    override val webpageMap: MapProperty<String, String> = project.objects.mapProperty()

    @OutputDirectory
    override val outputDirectory: DirectoryProperty = project.objects.directoryProperty()

    init {
        outputs.upToDateWhen { false } // always consider this task out of date
    }

    @TaskAction
    fun deploy() {
        logger.info("Writing HTML...")
        val writer = WebpageWriter(
            icon.orNull,
            authorName.orNull, authorUrl.orNull,
            projectName.get(), projectDescription.orNull, projectUrl.orNull,
            headerButtons.get(), footerCredit.get(), outputDirectory.asFile.get()
        )
        writer.write("index.html", WebpageWriter.getIndexSection(markdownFile.asFile.orNull))
        webpageMap.get().forEach { (fileName, rawSection) -> writer.write(fileName, rawSection) }
        logger.info("Done")
    }
}
