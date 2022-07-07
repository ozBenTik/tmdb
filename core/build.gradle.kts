import buildSrc.Modules.MODEL

plugins {
    id ("core-library-plugin")
}
dependencies {
    implementation(project(MODEL))
}