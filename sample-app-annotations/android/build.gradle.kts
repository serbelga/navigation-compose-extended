plugins {
    alias(deps.plugins.android.application)
    alias(deps.plugins.jetbrains.compose)
    alias(deps.plugins.jetbrains.kotlin.composeCompiler)
    alias(deps.plugins.sergiobelda.convention.spotless)
}

android {
    namespace = "dev.sergiobelda.navigation.compose.extended.sample.annotations.android"
    compileSdk = deps.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "dev.sergiobelda.navigation.compose.extended.sample.annotations"
        minSdk = deps.versions.android.minSdk.get().toInt()
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
    implementation(projects.sampleAppAnnotations.shared)
    implementation(deps.androidx.activityCompose)
}
