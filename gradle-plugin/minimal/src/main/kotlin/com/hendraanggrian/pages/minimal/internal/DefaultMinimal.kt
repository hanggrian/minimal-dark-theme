package com.hendraanggrian.pages.minimal.internal

import com.hendraanggrian.pages.minimal.HeaderButton
import com.hendraanggrian.pages.minimal.HeaderButtonsScope
import com.hendraanggrian.pages.minimal.Minimal
import com.hendraanggrian.pages.minimal.PagesScope
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.mapProperty
import org.gradle.kotlin.dsl.property
import java.io.File

open class DefaultMinimal(project: Project) : Minimal, HeaderButtonsScope, PagesScope {

    override val accentColor: Property<String> = project.objects.property<String>()
        .convention("#448aff")

    override val accentLightHoverColor: Property<String> = project.objects.property<String>()
        .convention("#005ecb")

    override val accentDarkHoverColor: Property<String> = project.objects.property<String>()
        .convention("#83b9ff")

    override val icon: Property<String> = project.objects.property()

    override val authorName: Property<String> = project.objects.property()

    override val authorUrl: Property<String> = project.objects.property()

    override val projectName: Property<String> = project.objects.property<String>()
        .convention(project.name)

    override val projectDescription: Property<String> = project.objects.property()

    override val projectUrl: Property<String> = project.objects.property()

    override val headerButtons: ListProperty<HeaderButton> = project.objects.listProperty<HeaderButton>()
        .convention(listOf())

    override val footerCredit: Property<Boolean> = project.objects.property<Boolean>()
        .convention(true)

    override val pagesMap: MapProperty<String, String> = project.objects.mapProperty<String, String>()
        .convention(mapOf())

    override val outputDirectory: DirectoryProperty = project.objects.directoryProperty()
        .convention(project.layout.buildDirectory.dir("minimal"))

    override fun headerButtons(action: Action<HeaderButtonsScope>) = action(this)

    override fun pages(action: Action<PagesScope>) = action(this)

    override fun button(line1: String, line2: String, url: String) {
        if (headerButtons.get().size >= 3) {
            error("Header buttons are capped at 3")
        }
        headerButtons.add(HeaderButton(line1, line2, url))
    }

    override fun page(fileName: String, raw: String) {
        pagesMap.put(fileName, raw)
    }

    override fun page(fileName: String, markdownFile: File) {
        page(fileName, htmlRenderer.render(parser.parse(markdownFile.readText())))
    }

    private val extensions = listOf(TablesExtension.create())
    private val htmlRenderer = HtmlRenderer.builder().extensions(extensions).build()
    private val parser = Parser.builder().extensions(extensions).build()
}
