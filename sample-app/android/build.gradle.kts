plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.kotlin.composeCompiler)
    id("dev.sergiobelda.gradle.spotless")
}

android {
    namespace = "dev.sergiobelda.navigation.compose.extended.sample.app.android"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        applicationId = "dev.sergiobelda.navigation.compose.extended.sample.app"
        minSdk = libs.versions.androidMinSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    kotlin {
        jvmToolchain(libs.versions.jdk.get().toInt())
    }
}

dependencies {
    implementation(projects.sampleApp.shared)
    implementation(libs.androidx.activity.compose)
}
