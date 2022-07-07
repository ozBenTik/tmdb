import buildSrc.Modules.CORE
import buildSrc.Modules.MODEL

plugins {
    id ("domain-library-plugin")
}
dependencies {
    implementation(project(CORE))
}