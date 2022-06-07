group = RELEASE_GROUP
version = RELEASE_VERSION

plugins {
    `kotlin-dsl`
    dokka
    `gradle-publish`
}

kotlin {
    jvmToolchain {
        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(8))
    }
}

gradlePlugin {
    plugins.register("minimalPlugin") {
        id = "$RELEASE_GROUP.$RELEASE_ARTIFACT"
        implementationClass = "$RELEASE_GROUP.$RELEASE_ARTIFACT.MinimalPlugin"
        displayName = "Minimal Theme Plugin"
        description = RELEASE_DESCRIPTION
    }
    testSourceSets(sourceSets.test.get())
}

pluginBundle {
    website = RELEASE_GITHUB
    vcsUrl = "$RELEASE_GITHUB.git"
    description = RELEASE_DESCRIPTION
    tags = listOf("website", "github-pages")
}

dependencies {
    implementation(kotlin("stdlib", VERSION_KOTLIN))
    implementation(kotlinx("coroutines-core", VERSION_COROUTINES))
    implementation(kotlinx("html-jvm", VERSION_HTML))
    implementation(commonmark)
    testImplementation(gradleTestKit())
    testImplementation(kotlin("test-junit", VERSION_KOTLIN))
}

tasks {
    dokkaHtml {
        outputDirectory.set(buildDir.resolve("dokka/dokka"))
    }
}

ktlint()