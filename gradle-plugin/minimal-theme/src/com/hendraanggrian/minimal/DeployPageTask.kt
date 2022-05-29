package com.hendraanggrian.minimal

import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.footer
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.header
import kotlinx.html.html
import kotlinx.html.id
import kotlinx.html.li
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.onClick
import kotlinx.html.p
import kotlinx.html.script
import kotlinx.html.section
import kotlinx.html.small
import kotlinx.html.span
import kotlinx.html.strong
import kotlinx.html.title
import kotlinx.html.ul
import kotlinx.html.unsafe
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.property
import java.io.FileWriter
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

/** Task to run when `deployPage` command is executed. */
open class DeployPageTask : DefaultTask() {

    /**
     * Accent color of the webpage.
     * Default is material color `Blue A200`.
     */
    @Input
    val accentColor: Property<String> = project.objects.property<String>()
        .convention("#448aff")

    /**
     * Accent color of the webpage when hovered in light theme.
     * Default is material color `Blue A200 Dark`.
     */
    @Input
    val accentLightHoverCover: Property<String> = project.objects.property<String>()
        .convention("#005ecb")

    /**
     * Accent color of the webpage when hovered in dark theme.
     * Default is material color `Blue A200 Light`.
     */
    @Input
    val accentDarkHoverColor: Property<String> = project.objects.property<String>()
        .convention("#83b9ff")

    /**
     * Author full name in title and footer.
     * If left empty, corresponding tag in footer is removed but title will still show project name.
     */
    @Optional
    @Input
    val authorName: Property<String> = project.objects.property()

    /**
     * Author website url in footer.
     * If left empty, author information in footer will not be clickable.
     */
    @Optional
    @Input
    val authorUrl: Property<String> = project.objects.property()

    /**
     * Project full name in header.
     * If left empty, module name will be used.
     */
    @Input
    val projectName: Property<String> = project.objects.property<String>()
        .convention(project.name)

    /**
     * Project description in header.
     * If left empty, corresponding tag in header is removed.
     */
    @Optional
    @Input
    val projectDescription: Property<String> = project.objects.property()

    /**
     * Project website url in header.
     * If left empty, corresponding tag in header is removed.
     */
    @Optional
    @Input
    val projectUrl: Property<String> = project.objects.property()

    /**
     * Small theme credit in footer.
     * Enabled by default.
     */
    @Input
    val showThemeCredit: Property<Boolean> = project.objects.property<Boolean>()
        .convention(true)

    /**
     * Markdown file which will be converted to section.
     * If empty, the whole section is blank.
     */
    @Optional
    @InputFile
    val markdownFile: RegularFileProperty = project.objects.fileProperty()

    /**
     * Output directory of this task.
     * Default is `$projectDir/build/minimal`.
     */
    @OutputDirectory
    val outputDirectory: DirectoryProperty = project.objects.directoryProperty()
        .convention(project.layout.buildDirectory.dir("minimal"))

    @get:Internal
    internal val headerButtons: ListProperty<HeaderButton> = project.objects.listProperty<HeaderButton>()
        .convention(listOf())

    /**
     * Opens up DSL to create side-by-side buttons in header.
     * Header buttons are capped at 3.
     */
    fun headerButtons(action: Action<HeaderButtonsScope>) {
        action.execute(HeaderButtonsImpl())
    }

    init {
        outputs.upToDateWhen { false } // always consider this task out of date
    }

    @TaskAction
    fun deploy() {
        writeToDir("images", "dark_mode.svg" to dark_mode_svg, "light_mode.svg" to light_mode_svg)
        writeToDir(
            "styles",
            "main.css" to getMainCss(
                accentColor.get(),
                accentLightHoverCover.get(),
                accentDarkHoverColor.get(),
                headerButtons.get().size
            ),
            "pygment_trac.css" to pygment_trac_css
        )
        writeToDir("scripts", "scale.fix.js" to scale_fix_js, "theme.js" to theme_js)

        val document = createHTMLDocument().html {
            head {
                meta(content = "chrome=1") { httpEquiv = "X-UA-Compatible" }
                title(authorName.orNull?.let { "${projectName.get()} by $it" } ?: projectName.get())
                link(rel = "stylesheet", href = "styles/main.css")
                link(rel = "stylesheet", href = "styles/pygment_trac.css")
                meta(name = "viewport", content = "width=device-width")
                script(src = "scripts/theme.js") { }
            }
            body {
                div(classes = "wrapper") {
                    header {
                        h1 { text(projectName.get()) }
                        projectDescription.orNull?.let { p { text(it) } }
                        projectUrl.orNull?.let { url ->
                            p(classes = "view") {
                                a(href = url) {
                                    if ("github.com" in url) {
                                        text("View the Project on GitHub ")
                                        val parts = when {
                                            !url.endsWith('/') -> url
                                            else -> url.substring(0, url.lastIndex - 1)
                                        }.split('/').reversed()
                                        small { text("${parts[1]}/${parts[0]}") }
                                    } else {
                                        text("View the Project")
                                    }
                                }
                            }
                        }
                        if (headerButtons.get().isNotEmpty()) {
                            ul {
                                headerButtons.get().forEach { (line1, line2, url) ->
                                    li { a(href = url) { text(line1); strong { text(line2) } } }
                                }
                            }
                        }
                    }
                    section {
                        markdownFile.asFile.orNull?.let {
                            unsafe {
                                val extensions = listOf(TablesExtension.create())
                                raw(
                                    HtmlRenderer.builder().extensions(extensions).build().render(
                                        Parser.builder().extensions(extensions).build().parse(it.readText())
                                    )
                                )
                            }
                        }
                    }
                    footer {
                        p {
                            button {
                                title = "Toggle dark mode"
                                onClick = "toggleDarkMode()"
                                span { id = "theme-toggle" }
                            }
                        }
                        p {
                            if (authorName.isPresent && authorUrl.isPresent) {
                                text("This project is maintained by ")
                                a(href = authorUrl.get()) { text(authorName.get()) }
                            } else if (authorName.isPresent) {
                                text("This project is maintained by ${authorName.get()}")
                            }
                        }
                        if (showThemeCredit.get()) {
                            small {
                                text("Hosted on GitHub Pages — Theme by ")
                                a(href = "https://github.com/hendraanggrian/minimal-theme") { text("minimal") }
                            }
                        }
                    }
                }
                script(src = "scripts/scale.fix.js") { }
            }
        }
        TransformerFactory.newInstance().newTransformer().transform(
            DOMSource(document),
            StreamResult(FileWriter(outputDirectory.asFile.get().resolve("index.html")))
        )
    }

    private fun writeToDir(directory: String, vararg contents: Pair<String, String>) {
        val targetDir = outputDirectory.asFile.get().resolve(directory)
        targetDir.mkdir()
        contents.forEach { targetDir.resolve(it.first).writeText(it.second) }
    }

    private inner class HeaderButtonsImpl : HeaderButtonsScope {
        override fun button(line1: String, line2: String, url: String) {
            if (headerButtons.get().size >= 3) {
                error("Header buttons are capped at 3")
            }
            headerButtons.add(HeaderButton(line1, line2, url))
        }
    }
}
