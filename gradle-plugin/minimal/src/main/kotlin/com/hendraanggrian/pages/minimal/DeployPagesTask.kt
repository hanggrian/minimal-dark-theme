package com.hendraanggrian.pages.minimal

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
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.mapProperty
import org.gradle.kotlin.dsl.property
import java.io.FileWriter
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

/** Task to run when `deployWebpages` command is executed. */
open class DeployPagesTask : DefaultTask(), PagesDeployment {

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
    override val headerButtons: ListProperty<HeaderButton> = project.objects.listProperty()

    @Input
    override val footerCredit: Property<Boolean> = project.objects.property()

    @Input
    override val pagesMap: MapProperty<String, String> = project.objects.mapProperty()

    @OutputDirectory
    override val outputDirectory: DirectoryProperty = project.objects.directoryProperty()

    init {
        outputs.upToDateWhen { false } // always consider this task out of date
    }

    private val transformer: Transformer = TransformerFactory.newInstance().newTransformer()

    @TaskAction
    fun deploy() {
        check(pagesMap.get().isNotEmpty()) { "No pages to write" }
        pagesMap.get().forEach { (fileName, raw) ->
            logger.info("Writing '$fileName'...")
            val document = createHTMLDocument().html {
                head {
                    meta(charset = "utf-8")
                    meta(content = "chrome=1") { httpEquiv = "X-UA-Compatible" }
                    title(authorName.orNull?.let { "${projectName.get()} by $it" } ?: projectName.get())
                    if (icon.isPresent) {
                        link(rel = "icon", href = icon.get())
                    }
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
                        section { unsafe { +raw } }
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
                            if (footerCredit.get()) {
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
            transformer.transform(
                DOMSource(document),
                StreamResult(FileWriter(outputDirectory.asFile.get().resolve(fileName)))
            )
        }
        logger.info("Done")
    }
}
