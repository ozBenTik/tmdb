import buildSrc.Modules.MODEL

plugins {
    id ("core-library-plugin")
}
dependencies {
    api(project(MODEL))
}