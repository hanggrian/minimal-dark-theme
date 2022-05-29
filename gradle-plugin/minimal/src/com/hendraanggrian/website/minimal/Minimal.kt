package com.hendraanggrian.website.minimal

import org.gradle.api.Action

interface Minimal : DeployResourcesSpec, DeployWebpagesSpec {

    /**
     * Opens up DSL to create side-by-side buttons in header.
     * Header buttons are capped at 3.
     */
    fun headerButtons(action: Action<HeaderButtonsScope>)

    /**
     * Create additional custom webpage using minimal theme.
     * @param fileName usually ends with `.html`.
     * @param rawSection raw HTML codes inside section tag of this webpage.
     */
    fun webpage(fileName: String, rawSection: String)

    /** Builder instance to modify header buttons. */
    interface HeaderButtonsScope {
        fun button(line1: String, line2: String, url: String)
    }

    data class HeaderButton(
        val line1: String,
        val line2: String,
        val url: String
    )
}
