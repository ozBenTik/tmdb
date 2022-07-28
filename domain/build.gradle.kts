import buildSrc.Modules.CORE

plugins {
    id ("domain-library-plugin")
}
dependencies {
    api(project(CORE))

    implementation(platform(buildSrc.Libs.Firebase.bom))
    implementation(buildSrc.Libs.Firebase.firebaseAuth)
}