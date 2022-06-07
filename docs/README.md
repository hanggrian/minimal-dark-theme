[![Plugin Portal](https://img.shields.io/maven-metadata/v?label=plugin-portal&metadataUrl=https%3A%2F%2Fplugins.gradle.org%2Fm2%2Fcom%2Fhendraanggrian%2Fpages%2Fminimal%2Fcom.hendraanggrian.pages.minimal.gradle.plugin%2Fmaven-metadata.xml)](https://plugins.gradle.org/plugin/com.hendraanggrian.pages.minimal)
[![Travis CI](https://img.shields.io/travis/com/hendraanggrian/minimal-theme)](https://travis-ci.com/github/hendraanggrian/minimal-theme)
[![OpenJDK](https://img.shields.io/badge/JDK-1.8+-orange)](https://openjdk.java.net/projects/jdk8)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-ff4081)](https://ktlint.github.io)

Minimal Theme
=============

![](images/preview.png)

A simple single page website theme with toggleable dark mode. Originally from [Minimal](https://github.com/orderedlist/minimal), this fork specializes in dark mode and generating webpages by command line or Gradle plugin.

Head to [repository website](https://hendraanggrian.com/minimal-theme) for demo of the theme.

Usage
-----

The raw source code is located in root directory (`images`, `scripts`, `styles`, `index.html`), they can be used to replicate the page by manually editing `index.html`.

### [Command Line](cli.md)

Generates complete directory with data pulled from GitHub.

### [Gradle Plugin](gradle_plugin.md)

Allows maximum configuration in Gradle project.