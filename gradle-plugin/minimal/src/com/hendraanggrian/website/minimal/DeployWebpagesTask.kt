package com.hendraanggrian.website.minimal

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.mapProperty
import org.gradle.kotlin.dsl.property

/** Task to run when `deployWebpages` command is executed. */
open class DeployWebpagesTask : DefaultTask(), DeployWebpagesSpec {

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
    override val footerCredit: Property<Boolean> = project.objects.property()

    @Optional
    @InputFile
    override val markdownFile: RegularFileProperty = project.objects.fileProperty()

    @OutputDirectory
    override val outputDirectory: DirectoryProperty = project.objects.directoryProperty()

    @get:Internal
    internal val headerButtons: ListProperty<Minimal.HeaderButton> = project.objects.listProperty()

    @get:Internal
    internal val webpageMap: MapProperty<String, String> = project.objects.mapProperty()

    init {
        outputs.upToDateWhen { false } // always consider this task out of date
    }

    @TaskAction
    fun deploy() {
        logger.info("Writing HTML...")
        val writer = WebpageWriter(
            authorName.orNull, authorUrl.orNull,
            projectName.get(), projectDescription.orNull, projectUrl.orNull,
            headerButtons.get(), footerCredit.get(), outputDirectory.asFile.get()
        )
        writer.write("index.html", WebpageWriter.getIndexSection(markdownFile.asFile.orNull))
        webpageMap.get().forEach { (fileName, rawSection) -> writer.write(fileName, rawSection) }
        logger.info("Done")
    }
}
