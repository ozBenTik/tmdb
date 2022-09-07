package buildSrc.plugins

import buildSrc.AndroidConfig
import buildSrc.Libs
import buildSrc.productFlavorsConfig
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class UiLibraryPlugin : Plugin<Project> {

    private val Project.android: LibraryExtension
        get() = extensions.findByName("android") as? LibraryExtension
            ?: error("Not an Android module: $name")

    override fun apply(target: Project) {
        with(target) {
            applyPlugins()
            androidConfig()
            productFlavorsConfig()
            dependenciesConfig()
        }
    }

    private fun Project.applyPlugins() {
        plugins.run {
            apply("com.android.library")
            apply("kotlin-android")
            apply("kotlin-parcelize")
            apply("kotlin-kapt")
            apply("dagger.hilt.android.plugin")
        }
    }

    private fun Project.androidConfig() {
        android.run {
            compileSdkVersion(AndroidConfig.compileSdk)
            defaultConfig {
                minSdk = AndroidConfig.minSdk
                targetSdk = AndroidConfig.targetSdk
//                versionCode = AndroidConfig.versionCode
//                versionName = AndroidConfig.versionName

                testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
            }

            compileOptions {
                sourceCompatibility = AndroidConfig.sourceCompatibility
                targetCompatibility = AndroidConfig.targetCompatibility
            }

            viewBinding.isEnabled = true
            composeOptions.kotlinCompilerExtensionVersion = Libs.AndroidX.Compose.kotlinCompilerExtensionVersion
            buildFeatures.compose = true

            packagingOptions {
                resources {
                    excludes += "META-INF/main.kotlin_module"
                }
            }
        }
    }

    private fun Project.dependenciesConfig() {
        dependencies {
            "implementation"(Libs.AndroidX.coreKtx)
            "implementation"(Libs.Google.material)

            "implementation"(Libs.AndroidX.Lifecycle.viewmodel)
            "implementation"(Libs.AndroidX.Lifecycle.runtime)
            "implementation"(Libs.AndroidX.Paging.runtime)
            "implementation"(Libs.AndroidX.SwipeRefreshLayout.swiperefreshlayout)

            "implementation"(Libs.Hilt.android)
            "kapt"(Libs.Hilt.compiler)

            "implementation"(Libs.Coroutines.android)
            "implementation"(Libs.Coroutines.core)

            "implementation"(Libs.AndroidX.Navigation.fragment)
            "implementation"(Libs.AndroidX.Navigation.uiKtx)

            // Glide
            "implementation"(Libs.Glide.glide)
            "kapt"(Libs.Glide.compiler)

            // Compose
            "implementation"

            "implementation"(Libs.AndroidX.Compose.layout)
            "implementation"(Libs.AndroidX.Compose.material)
            "implementation"(Libs.AndroidX.Compose.Material3.material3)
            "implementation"(Libs.AndroidX.Compose.activityCompose)
            "implementation"(Libs.AndroidX.Compose.viewModelCompose)
            "implementation"(Libs.AndroidX.Compose.materialIconsExtended)
            "implementation"(Libs.AndroidX.Compose.tooling)
            "implementation"(Libs.AndroidX.Compose.uiUtil)
            "implementation"(Libs.AndroidX.Compose.runtime)
            "implementation"(Libs.AndroidX.Compose.runtimeLivedata)
            "implementation"(Libs.AndroidX.Compose.viewBinding)
            "implementation"(Libs.AndroidX.Compose.themeAdapter)
            "implementation"(Libs.AndroidX.Compose.accompanistTheme)
            "implementation"(Libs.AndroidX.Compose.preview)
            "implementation"(Libs.AndroidX.Compose.paging)
            "implementation"(Libs.AndroidX.Compose.swipeToRefresh)
            "debugImplementation"(Libs.AndroidX.Compose.uiTestManifest)
            "androidTestImplementation"(Libs.AndroidX.Compose.test)
            "androidTestImplementation"(Libs.AndroidX.Compose.uiTest)
            "androidTestImplementation"(Libs.AndroidX.Compose.manifest)
        }
    }
}