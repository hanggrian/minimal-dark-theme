package com.hendraanggrian.website

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

class DeployWebpagesTaskTest {
    @Rule @JvmField val testProjectDir = TemporaryFolder()
    private lateinit var buildFile: File
    private lateinit var runner: GradleRunner

    @BeforeTest
    @Throws(IOException::class)
    fun setup() {
        testProjectDir.newFile("settings.gradle.kts").writeText(
            """
            rootProject.name = "deploy-webpages-test"
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
                id("com.hendraanggrian.website.minimal")
            }
            """.trimIndent()
        )
        runner.withArguments("deployWebpages").build().let {
            assertEquals(TaskOutcome.SUCCESS, it.task(":deployWebpages")!!.outcome)
        }
        testProjectDir.root.resolve("build/minimal/index.html").readText().let {
            assertTrue("deploy-webpages-test" in it)
        }
    }

    @Test
    fun fullConfiguration() {
        testProjectDir.newFile("Content.md").writeText(
            """
            # Hello
            ## World
        """.trimIndent()
        )
        buildFile.writeText(
            """
            plugins {
                id("com.hendraanggrian.website.minimal")
            }
            minimal {
                accentColor.set("#ff0000")
                accentLightHoverColor.set("#00ff00")
                accentDarkHoverColor.set("#0000ff")
                authorName.set("Cool Dude")
                authorUrl.set("https://www.google.com")
                projectName.set("Cool Stuff")
                projectDescription.set("Cures cancer")
                projectUrl.set("https://www.google.com")
                footerCredit.set(false)
                markdownFile.set(file("Content.md"))
                outputDirectory.set(buildDir.resolve("custom-dir"))
                headerButtons {
                    button("Rate", "Us", "https://www.google.com")
                    button("Leave", "Review", "https://www.google.com")
                    button("Report", "User", "https://www.google.com")
                }
                webpage("additional.html", "<p>This is an additional webpage.</p>")
            }
            """.trimIndent()
        )
        runner.withArguments("deployWebpages").build().let {
            assertEquals(TaskOutcome.SUCCESS, it.task(":deployWebpages")!!.outcome)
        }
        testProjectDir.root.resolve("build/custom-dir/index.html").readText().let {
            assertTrue("Cool Dude" in it)
            assertTrue("https://www.google.com" in it)
            assertTrue("Cool Stuff" in it)
            assertTrue("Cures cancer" in it)
            assertTrue("Hello" in it)
            assertTrue("World" in it)
        }
        testProjectDir.root.resolve("build/custom-dir/additional.html").readText().let {
            assertTrue("This is an additional webpage." in it)
        }
    }
}