group = RELEASE_GROUP
version = RELEASE_VERSION

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    dokka
    `gradle-publish`
}

sourceSets {
    main {
        java.srcDir("src")
        resources.srcDir("res")
    }
    test {
        java.srcDir("tests/src")
        resources.srcDir("tests/res")
    }
}

gradlePlugin {
    plugins.register("minimalPlugin") {
        id = "$RELEASE_GROUP.minimal"
        implementationClass = "$RELEASE_GROUP.minimal.MinimalPlugin"
        displayName = "Minimal Website Plugin"
        description = RELEASE_DESCRIPTION
    }
    testSourceSets(sourceSets.test.get())
}

ktlint()

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

pluginBundle {
    website = RELEASE_GITHUB
    vcsUrl = "$RELEASE_GITHUB.git"
    description = RELEASE_DESCRIPTION
    tags = listOf("website", "github-pages")
}