[![version](https://img.shields.io/maven-metadata/v?label=plugin-portal&metadataUrl=https%3A%2F%2Fplugins.gradle.org%2Fm2%2Fcom%2Fhendraanggrian%2Fminimal-theme%2Fcom.hendraanggrian.minimal-theme.gradle.plugin%2Fmaven-metadata.xml)](https://plugins.gradle.org/plugin/com.hendraanggrian.minimal-theme)
[![build](https://img.shields.io/travis/com/hendraanggrian/minimal-theme)](https://travis-ci.com/github/hendraanggrian/minimal-theme)
[![analysis](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081)](https://ktlint.github.io)

Minimal Theme Gradle Plugin
===========================

A plugin that writes custom webpage with *minimal* theme.

Download
--------

Using plugins DSL:

```gradle
plugins {
    id('com.hendraanggrian.minimal-theme') version "$version"
}
```

Using legacy plugin application:

```gradle
buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.hendraanggrian:minimal-theme:$version")
    }
}

apply plugin: 'com.hendraanggrian.minimal-theme'
```

Usage
-----

Modify `deployPage` task accordingly. Visit [DeployPageTask.kt](../gradle-plugin/minimal-theme/src/com/hendraanggrian/minimal/DeployPageTask.kt) for full configuration details.

```gradle
plugins {
    id('com.hendraanggrian.minimal-theme')
}

tasks.deployPage {
    authorName.set('Hendra Anggrian')
    projectName.set('My Project')
    projectDescription.set('A very awesome project')
    headerButtons {
        button("Download", "Sources", "https://github.com/hendraanggrian/my-project/zipball/main")
    }
}
```