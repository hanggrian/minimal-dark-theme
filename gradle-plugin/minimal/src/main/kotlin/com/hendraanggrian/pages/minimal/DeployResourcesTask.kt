package com.hendraanggrian.pages.minimal

import com.hendraanggrian.pages.minimal.resources.dark_mode_svg
import com.hendraanggrian.pages.minimal.resources.getMainCss
import com.hendraanggrian.pages.minimal.resources.light_mode_svg
import com.hendraanggrian.pages.minimal.resources.pygment_trac_css
import com.hendraanggrian.pages.minimal.resources.scale_fix_js
import com.hendraanggrian.pages.minimal.resources.theme_js
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.property

/** Task to run when `deployResources` command is executed. */
open class DeployResourcesTask : DefaultTask(), ResourcesDeployment {

    @Input
    override val accentColor: Property<String> = project.objects.property()

    @Input
    override val accentLightHoverColor: Property<String> = project.objects.property()

    @Input
    override val accentDarkHoverColor: Property<String> = project.objects.property()

    @OutputDirectory
    override val outputDirectory: DirectoryProperty = project.objects.directoryProperty()

    /**
     * Determines the width of header button, capped at 3.
     * This value is automatically set when configuring [Minimal] extension.
     */
    @Input
    val headerButtonsSize: Property<Int> = project.objects.property()

    init {
        outputs.upToDateWhen { false } // always consider this task out of date
    }

    @TaskAction
    fun deploy() {
        check(headerButtonsSize.get() <= 3) { "Header button size is capped at 3" }
        writeToDir(
            "images",
            "dark_mode.svg" to dark_mode_svg,
            "light_mode.svg" to light_mode_svg
        )
        writeToDir(
            "styles",
            "main.css" to getMainCss(
                accentColor.get(), accentLightHoverColor.get(), accentDarkHoverColor.get(),
                headerButtonsSize.get()
            ),
            "pygment_trac.css" to pygment_trac_css
        )
        writeToDir(
            "scripts",
            "scale.fix.js" to scale_fix_js,
            "theme.js" to theme_js
        )
        logger.info("Done")
    }

    private fun writeToDir(directory: String, vararg contents: Pair<String, String>) {
        logger.info("Writing '$directory'...")
        val targetDir = outputDirectory.asFile.get().resolve(directory)
        targetDir.mkdir()
        contents.forEach { targetDir.resolve(it.first).writeText(it.second) }
    }
}
