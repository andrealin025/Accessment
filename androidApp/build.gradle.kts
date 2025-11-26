import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.starwarsapp.android"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.starwarsapp.android"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    
    flavorDimensions += "branding"
    productFlavors {
        create("blue") {
            dimension = "branding"
            applicationIdSuffix = ".blue"
            versionNameSuffix = "-blue"
            resValue("string", "app_name", "Star Wars Blue")
        }
        create("red") {
            dimension = "branding"
            applicationIdSuffix = ".red"
            versionNameSuffix = "-red"
            resValue("string", "app_name", "Star Wars Red")
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
}

dependencies {
    implementation(project(":shared"))
    
    implementation(compose.bom(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.activity)
    
    implementation(libs.lifecycle.viewmodel.compose)
}

