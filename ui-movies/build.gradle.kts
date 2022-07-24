import buildSrc.Modules.CORE
import buildSrc.Modules.CORE_UI
import buildSrc.Modules.DOMAIN
import buildSrc.Modules.UI_LOGIN

plugins {
    id ("ui-library-plugin")
}
dependencies {
    implementation(project(CORE_UI))
    implementation(project(CORE))
    implementation(project(DOMAIN))

    implementation(platform(buildSrc.Libs.Firebase.bom))
    implementation(buildSrc.Libs.Firebase.firebaseAuth)
}