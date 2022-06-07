package com.hendraanggrian.pages

import com.hendraanggrian.pages.minimal.MinimalPlugin
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import java.io.File
import java.io.IOException
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue

class MinimalFunctionalTest {
    @Rule @JvmField val testProjectDir = TemporaryFolder()
    private lateinit var buildFile: File
    private lateinit var runner: GradleRunner

    @BeforeTest
    @Throws(IOException::class)
    fun setup() {
        testProjectDir.newFile("settings.gradle.kts")
            .writeText("rootProject.name = \"minimal-functional-test\"")
        buildFile = testProjectDir.newFile("build.gradle.kts")
        runner = GradleRunner.create()
            .withPluginClasspath()
            .withProjectDir(testProjectDir.root)
            .withTestKitDir(testProjectDir.newFolder())
    }

    @Test
    fun tooManyHeaderButtons() {
        buildFile.writeText(
            """
            plugins {
                id("com.hendraanggrian.pages.minimal")
            }
            tasks.deployResources {
                headerButtonsSize.set(4)
            }
            """.trimIndent()
        )
        assertFails {
            runner.withArguments(MinimalPlugin.TASK_DEPLOY_RESOURCES).build()
                .task(":${MinimalPlugin.TASK_DEPLOY_RESOURCES}")
        }
    }

    @Test
    fun noIndexHtml() {
        buildFile.writeText(
            """
            plugins {
                id("com.hendraanggrian.pages.minimal")
            }
            """.trimIndent()
        )
        assertFails {
            runner.withArguments(MinimalPlugin.TASK_DEPLOY_PAGES).build()
                .task(":${MinimalPlugin.TASK_DEPLOY_PAGES}")
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
                id("com.hendraanggrian.pages.minimal")
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
                outputDirectory.set(buildDir.resolve("custom-dir"))
                headerButtons {
                    button("Rate", "Us", "https://www.google.com")
                    button("Leave", "Review", "https://www.google.com")
                    button("Report", "User", "https://www.google.com")
                }
                pages {
                    index(file("Content.md"))
                    page("additional.html", "<p>This is an additional webpage.</p>")
                }
            }
            """.trimIndent()
        )
        assertEquals(
            TaskOutcome.SUCCESS,
            runner.withArguments(MinimalPlugin.TASK_DEPLOY_PAGES).build()
                .task(":${MinimalPlugin.TASK_DEPLOY_PAGES}")!!.outcome
        )
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
