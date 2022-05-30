package com.hendraanggrian.pages.minimal

import org.gradle.api.Action
import java.io.Serializable

interface Minimal : DeployResourcesSpec, DeployPagesSpec {

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
        /**
         * Add header button.
         * @see HeaderButton
         */
        fun button(line1: String, line2: String, url: String)
    }

    /**
     * Header button data class.
     * @param line1 first line of text.
     * @param line2 second line of text, with stronger emphasis.
     * @param url absolute or relative path to redirect to on button click.
     */
    data class HeaderButton(val line1: String, val line2: String, val url: String) : Serializable
}
