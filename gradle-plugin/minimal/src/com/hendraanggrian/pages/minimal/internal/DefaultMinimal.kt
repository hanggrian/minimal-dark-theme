package com.hendraanggrian.pages.minimal.internal

import com.hendraanggrian.pages.minimal.Minimal
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.mapProperty
import org.gradle.kotlin.dsl.property

open class DefaultMinimal(project: Project) : Minimal {

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

    override val headerButtons: ListProperty<Minimal.HeaderButton> =
        project.objects.listProperty<Minimal.HeaderButton>().convention(listOf())

    override val footerCredit: Property<Boolean> = project.objects.property<Boolean>()
        .convention(true)

    override val markdownFile: RegularFileProperty = project.objects.fileProperty()

    override val webpageMap: MapProperty<String, String> = project.objects.mapProperty<String, String>()
        .convention(mapOf())

    override val outputDirectory: DirectoryProperty = project.objects.directoryProperty()
        .convention(project.layout.buildDirectory.dir("minimal"))

    override fun headerButtons(action: Action<Minimal.HeaderButtonsScope>) {
        action.execute(HeaderButtonsImpl())
    }

    override fun webpage(fileName: String, rawSection: String) {
        webpageMap.put(fileName, rawSection)
    }

    private inner class HeaderButtonsImpl : Minimal.HeaderButtonsScope {
        override fun button(line1: String, line2: String, url: String) {
            if (headerButtons.get().size >= 3) {
                error("Header buttons are capped at 3")
            }
            headerButtons.add(Minimal.HeaderButton(line1, line2, url))
        }
    }
}
