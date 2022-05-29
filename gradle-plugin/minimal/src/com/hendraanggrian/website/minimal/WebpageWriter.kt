package com.hendraanggrian.website.minimal

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
import java.io.File
import java.io.FileWriter
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

internal class WebpageWriter(
    private val authorName: String?,
    private val authorUrl: String?,
    private val projectName: String,
    private val projectDescription: String?,
    private val projectUrl: String?,
    private val headerButtons: List<Minimal.HeaderButton>,
    private val footerCredit: Boolean,
    private val outputDirectory: File
) {
    companion object {
        fun getIndexSection(markdownFile: File?): String {
            if (markdownFile == null) {
                return ""
            }
            val extensions = listOf(TablesExtension.create())
            return HtmlRenderer.builder().extensions(extensions).build().render(
                Parser.builder().extensions(extensions).build().parse(markdownFile.readText())
            )
        }
    }

    private val transformer: Transformer = TransformerFactory.newInstance().newTransformer()

    fun write(fileName: String, rawSection: String) {
        val document = createHTMLDocument().html {
            head {
                meta(content = "chrome=1") { httpEquiv = "X-UA-Compatible" }
                title(authorName?.let { "$projectName by $it" } ?: projectName)
                link(rel = "stylesheet", href = "styles/main.css")
                link(rel = "stylesheet", href = "styles/pygment_trac.css")
                meta(name = "viewport", content = "width=device-width")
                script(src = "scripts/theme.js") { }
            }
            body {
                div(classes = "wrapper") {
                    header {
                        h1 { text(projectName) }
                        projectDescription?.let { p { text(it) } }
                        projectUrl?.let { url ->
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
                        if (headerButtons.isNotEmpty()) {
                            ul {
                                headerButtons.forEach { (line1, line2, url) ->
                                    li { a(href = url) { text(line1); strong { text(line2) } } }
                                }
                            }
                        }
                    }
                    section { unsafe { +rawSection } }
                    footer {
                        p {
                            button {
                                title = "Toggle dark mode"
                                onClick = "toggleDarkMode()"
                                span { id = "theme-toggle" }
                            }
                        }
                        p {
                            if (authorName != null && authorUrl != null) {
                                text("This project is maintained by ")
                                a(href = authorUrl) { text(authorName) }
                            } else if (authorName != null) {
                                text("This project is maintained by $authorName")
                            }
                        }
                        if (footerCredit) {
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
        transformer.transform(DOMSource(document), StreamResult(FileWriter(outputDirectory.resolve(fileName))))
    }
}
