package buildSrc

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.dsl.DefaultConfig
import org.gradle.api.Project

fun Project.productFlavorsConfig(defaultConfigExtensions: (DefaultConfig.() -> Unit)? = null) {
    android.run {
        buildTypes {
            getByName("debug") {
                isMinifyEnabled = false
            }
            getByName("release") {
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android.txt"),
                    "proguard-rules.pro"
                )
                consumerProguardFiles("consumer-rules.pro")
            }
        }

        flavorDimensions(AndroidConfig.flavorDimensions)
        productFlavors {
            create("dev") {
                dimension = "mode"
            }
            create("prod") {
                dimension = "mode"
            }
        }
    }
}

val Project.android: BaseExtension
    get() = extensions.findByName("android") as? BaseExtension
        ?: error("Project '$name' is not an Android module")