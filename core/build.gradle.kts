import buildSrc.Modules.MODEL

plugins {
    id ("core-library-plugin")
}
dependencies {
    api(project(MODEL))

    implementation(platform(buildSrc.Libs.Firebase.bom))
    implementation(buildSrc.Libs.Firebase.firebaseAuth)
    implementation(buildSrc.Libs.Firebase.firebaseDb)
}