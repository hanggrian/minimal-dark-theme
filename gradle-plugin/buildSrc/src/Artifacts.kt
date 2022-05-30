internal typealias Dependencies = org.gradle.api.artifacts.dsl.DependencyHandler
internal typealias Plugins = org.gradle.plugin.use.PluginDependenciesSpec

val Dependencies.`gradle-publish` get() = "com.gradle.publish:plugin-publish-plugin:1.0.0-rc-2"
val Plugins.`gradle-publish` get() = id("com.gradle.plugin-publish")

const val VERSION_KOTLIN = "1.6.21"
const val VERSION_COROUTINES = "1.6.2"
const val VERSION_HTML = "0.7.5"
val Dependencies.dokka get() = "org.jetbrains.dokka:dokka-gradle-plugin:$VERSION_KOTLIN"
val Plugins.dokka get() = id("org.jetbrains.dokka")
fun Dependencies.kotlinx(module: String, version: String? = null) =
    "org.jetbrains.kotlinx:kotlinx-$module${version?.let { ":$it" }.orEmpty()}"

const val VERSION_COMMONMARK = "0.18.1"
val Dependencies.commonmark get() = "org.commonmark:commonmark-ext-gfm-tables:$VERSION_COMMONMARK"

val Dependencies.`git-publish` get() = "org.ajoberstar:gradle-git-publish:3.0.0"
val Plugins.`git-publish` get() = id("org.ajoberstar.git-publish")