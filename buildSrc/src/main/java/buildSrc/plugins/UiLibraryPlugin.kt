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

            // Compose
            "implementation"(Libs.AndroidX.Compose.foundation)
            "implementation"(Libs.AndroidX.Compose.ui)
            "implementation"(Libs.AndroidX.Compose.liveData)
            "implementation"(Libs.AndroidX.Compose.material)
            "implementation"(Libs.AndroidX.Compose.materialIconsCore)
            "implementation"(Libs.AndroidX.Compose.materialIconsExtended)
            "implementation"(Libs.AndroidX.Compose.uiTooling)
            "implementation"(Libs.AndroidX.Lifecycle.activityCompose)
            "implementation"(Libs.AndroidX.Lifecycle.viewModelCompose)
        }
    }
}