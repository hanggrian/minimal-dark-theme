package com.hendraanggrian.pages.minimal

import java.io.File

/** Allows to add side-by-side buttons inside header tag. */
interface HeaderButtonsScope {
    /**
     * Add header button.
     * @param line1 button's top text.
     * @param line2 button's bottom text with stronger emphasis.
     * @param url to redirect on button click.
     */
    fun button(line1: String, line2: String, url: String)
}

/** Allows to add webpages. */
interface PagesScope {
    /**
     * Add custom webpage using raw string.
     * @param fileName usually ends with `.html`.
     * @param raw raw HTML codes.
     */
    fun page(fileName: String, raw: String)

    /**
     * Add custom webpage using markdown file.
     * @param fileName usually ends with `.html`.
     * @param markdownFile a markdown file.
     */
    fun page(fileName: String, markdownFile: File)

    /**
     * Add `index.html` using raw string.
     * @param raw raw HTML codes.
     */
    fun index(raw: String) = page("index.html", raw)

    /**
     * Add `index.html` using markdown file.
     * @param markdownFile a markdown file.
     */
    fun index(markdownFile: File) = page("index.html", markdownFile)
}
