plugins {
    id ("application-plugin")
    id("org.jetbrains.kotlin.android")
}
dependencies {
    implementation(project(buildSrc.Modules.CORE))
    implementation(project(buildSrc.Modules.CORE_UI))
    implementation(project(buildSrc.Modules.MODEL))
    implementation(project(buildSrc.Modules.DOMAIN))
    implementation(project(buildSrc.Modules.UI_PEOPLE))
    implementation(project(buildSrc.Modules.UI_MOVIES))
    implementation(project(buildSrc.Modules.UI_LOGIN))
    implementation(project(buildSrc.Modules.UI_USER))
    implementation(project(buildSrc.Modules.UI_TVSHOWS))

    implementation(platform(buildSrc.Libs.Firebase.bom))
    implementation(buildSrc.Libs.Firebase.firebaseAuth)
}