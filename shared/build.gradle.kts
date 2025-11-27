import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.starwarsapp.shared"
    compileSdk = 35
}

kotlin {
    applyDefaultHierarchyTemplate()
    
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_11)
                }
            }
        }
    }
    
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                // Coroutines
                implementation(libs.kotlinx.coroutines.core)
                
                // Ktor
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.ktor.client.logging)
            }
        }
        
        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.android)
                implementation(libs.kotlinx.coroutines.android)
            }
        }
        
        val iosMain by getting {
            dependencies {
                implementation(libs.ktor.client.ios)
            }
        }
    }
}
