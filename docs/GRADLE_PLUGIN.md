Minimal Theme Gradle Plugin
===========================

A plugin that writes custom webpage with *minimal* theme.

Download
--------

Using plugins DSL:

```gradle
plugins {
    id('com.hendraanggrian.website.minimal') version "$version"
}
```

Using legacy plugin application:

```gradle
buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.hendraanggrian:website:minimal:$version")
    }
}

apply plugin: 'com.hendraanggrian.website.minimal'
```

Usage
-----

Visit [DeployResourcesSpec.kt](../gradle-plugin/minimal/src/com/hendraanggrian/website/minimal/DeployResourcesSpec.kt) and [DeployWebpagesSpec.kt](../gradle-plugin/minimal/src/com/hendraanggrian/website/minimal/DeployWebpagesSpec.kt) for full configuration details.

```gradle
plugins {
    id('com.hendraanggrian.website.minimal')
}

minimal {
    authorName.set('Hendra Anggrian')
    projectName.set('My Project')
    projectDescription.set('A very awesome project')
    headerButtons {
        button("Download", "Sources", "https://github.com/hendraanggrian/my-project/zipball/main")
    }
}
```

### With [Application](https://docs.gradle.org/current/userguide/application_plugin.html) plugin

When application plugin is applied, application name will be used as project name convention.

```gradle
plugins {
    id('application')
    id('com.hendraanggrian.website.minimal')
}

application {
    applicationName.set('My Project')
    mainClassName.set('com.hendraanggrian.myproject.MainClass')
}
```