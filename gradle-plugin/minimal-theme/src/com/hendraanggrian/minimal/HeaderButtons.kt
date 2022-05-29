package com.hendraanggrian.minimal

/** Builder instance to modify header buttons. */
interface HeaderButtonsScope {
    fun button(line1: String, line2: String, url: String)
}

internal data class HeaderButton(
    val line1: String,
    val line2: String,
    val url: String
)
