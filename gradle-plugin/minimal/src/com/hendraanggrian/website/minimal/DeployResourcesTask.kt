package com.hendraanggrian.website.minimal

import com.hendraanggrian.website.minimal.resources.dark_mode_svg
import com.hendraanggrian.website.minimal.resources.getMainCss
import com.hendraanggrian.website.minimal.resources.light_mode_svg
import com.hendraanggrian.website.minimal.resources.pygment_trac_css
import com.hendraanggrian.website.minimal.resources.scale_fix_js
import com.hendraanggrian.website.minimal.resources.theme_js
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.property

/** Task to run when `deployResources` command is executed. */
open class DeployResourcesTask : DefaultTask(), DeployResourcesSpec {

    @Optional
    @Input
    override val logo: Property<String> = project.objects.property()

    @Input
    override val accentColor: Property<String> = project.objects.property()

    @Input
    override val accentLightHoverColor: Property<String> = project.objects.property()

    @Input
    override val accentDarkHoverColor: Property<String> = project.objects.property()

    @OutputDirectory
    override val outputDirectory: DirectoryProperty = project.objects.directoryProperty()

    /** Determines the width of header button, capped at 3. */
    @Input
    val headerButtonsSize: Property<Int> = project.objects.property()

    init {
        outputs.upToDateWhen { false } // always consider this task out of date
    }

    @TaskAction
    fun deploy() {
        logger.info("Writing static resources...")
        writeToDir("images", "dark_mode.svg" to dark_mode_svg, "light_mode.svg" to light_mode_svg)
        writeToDir(
            "styles",
            "main.css" to getMainCss(
                accentColor.get(), accentLightHoverColor.get(), accentDarkHoverColor.get(),
                headerButtonsSize.get()
            ),
            "pygment_trac.css" to pygment_trac_css
        )
        writeToDir("scripts", "scale.fix.js" to scale_fix_js, "theme.js" to theme_js)
        logger.info("Done")
    }

    private fun writeToDir(directory: String, vararg contents: Pair<String, String>) {
        val targetDir = outputDirectory.asFile.get().resolve(directory)
        targetDir.mkdir()
        contents.forEach { targetDir.resolve(it.first).writeText(it.second) }
    }
}
