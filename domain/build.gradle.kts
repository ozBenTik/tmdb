import buildSrc.Modules.CORE

plugins {
    id ("domain-library-plugin")
}
dependencies {
    api(project(CORE))
}