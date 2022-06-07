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
import kotlin.test.assertTrue

class MinimalIntegrationTest {
    @Rule @JvmField val testProjectDir = TemporaryFolder()
    private lateinit var buildFile: File
    private lateinit var runner: GradleRunner

    @BeforeTest
    @Throws(IOException::class)
    fun setup() {
        testProjectDir.newFile("settings.gradle.kts")
            .writeText("rootProject.name = \"minimal-integration-test\"")
        buildFile = testProjectDir.newFile("build.gradle.kts")
        runner = GradleRunner.create()
            .withPluginClasspath()
            .withProjectDir(testProjectDir.root)
            .withTestKitDir(testProjectDir.newFolder())
    }

    @Test
    fun withApplicationPlugin() {
        buildFile.writeText(
            """
            plugins {
                application
                id("com.hendraanggrian.pages.minimal")
            }
            application {
                applicationName = "My App"
            }
            minimal {
                pages {
                    index("")
                }
            }
            """.trimIndent()
        )
        assertEquals(
            TaskOutcome.SUCCESS,
            runner.withArguments(MinimalPlugin.TASK_DEPLOY_PAGES).build()
                .task(":${MinimalPlugin.TASK_DEPLOY_PAGES}")!!.outcome
        )
        assertTrue("My App" in testProjectDir.root.resolve("build/minimal/index.html").readText())
    }
}
