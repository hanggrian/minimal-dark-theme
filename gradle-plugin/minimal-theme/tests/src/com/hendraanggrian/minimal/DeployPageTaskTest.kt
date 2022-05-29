package com.hendraanggrian.minimal

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import java.io.File
import java.io.IOException
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DeployPageTaskTest {
    @Rule @JvmField val testProjectDir = TemporaryFolder()
    private lateinit var buildFile: File
    private lateinit var runner: GradleRunner

    @BeforeTest
    @Throws(IOException::class)
    fun setup() {
        testProjectDir.newFile("settings.gradle.kts").writeText(
            """
            rootProject.name = "deploy-page-test"
            """.trimIndent()
        )
        buildFile = testProjectDir.newFile("build.gradle.kts")
        runner = GradleRunner.create()
            .withPluginClasspath()
            .withProjectDir(testProjectDir.root)
            .withTestKitDir(testProjectDir.newFolder())
    }

    @Test
    fun noConfiguration() {
        buildFile.writeText(
            """
            plugins {
                id("com.hendraanggrian.minimal-theme")
            }
            """.trimIndent()
        )
        runner.withArguments("deployPage").build().let {
            assertEquals(TaskOutcome.SUCCESS, it.task(":deployPage")!!.outcome)
        }
        assertTrue(testProjectDir.root.resolve("build/minimal/index.html").exists())
    }

    @Test
    fun fullConfiguration() {
        testProjectDir.newFile("Content.md").writeText("""
            # Hello
            ## World
        """.trimIndent())
        buildFile.writeText(
            """
            plugins {
                id("com.hendraanggrian.minimal-theme")
            }
            tasks.deployPage {
                accentColor.set("#ff0000")
                accentLightHoverCover.set("#00ff00")
                accentDarkHoverColor.set("#0000ff")
                authorName.set("Cool Dude")
                authorUrl.set("https://www.google.com")
                projectName.set("Cool Stuff")
                projectDescription.set("Cures cancer")
                projectUrl.set("https://www.google.com")
                showThemeCredit.set(false)
                markdownFile.set(file("Content.md"))
                outputDirectory.set(buildDir)
                headerButtons {
                    button("Rate", "Us", "https://www.google.com")
                    button("Leave", "Review", "https://www.google.com")
                    button("Report", "User", "https://www.google.com")
                }
            }
            """.trimIndent()
        )
        runner.withArguments("deployPage").build().let {
            assertEquals(TaskOutcome.SUCCESS, it.task(":deployPage")!!.outcome)
        }
        testProjectDir.root.resolve("build/index.html").readText().let {
            assertTrue("Cool Dude" in it)
            assertTrue("https://www.google.com" in it)
            assertTrue("Cool Stuff" in it)
            assertTrue("Cures cancer" in it)
            assertTrue("Hello" in it)
            assertTrue("World" in it)
        }
    }
}