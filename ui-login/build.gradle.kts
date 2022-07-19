plugins {
    id ("ui-library-plugin")
    id("org.jetbrains.kotlin.android")
}
dependencies {
    implementation(project(buildSrc.Modules.CORE_UI))
    implementation(project(buildSrc.Modules.CORE))
    implementation(project(buildSrc.Modules.DOMAIN))
}