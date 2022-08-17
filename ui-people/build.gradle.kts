import buildSrc.Modules.CORE
import buildSrc.Modules.CORE_UI
import buildSrc.Modules.DOMAIN

plugins {
    id ("ui-library-plugin")
}
dependencies {
    api(project(CORE))
    api(project(CORE_UI))
//    implementation(project(CORE))
    implementation(project(DOMAIN))
}