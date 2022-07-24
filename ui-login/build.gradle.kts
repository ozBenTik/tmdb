plugins {
    id ("ui-library-plugin")
}
dependencies {
    implementation(project(buildSrc.Modules.CORE_UI))
    implementation(project(buildSrc.Modules.CORE))
    implementation(project(buildSrc.Modules.DOMAIN))

    implementation(platform(buildSrc.Libs.Firebase.bom))
    implementation(buildSrc.Libs.Firebase.firebaseAuth)
}