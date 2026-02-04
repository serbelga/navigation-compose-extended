plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.dokka)
    alias(libs.plugins.jetbrains.kotlin.composeCompiler)
    alias(libs.plugins.vanniktechMavenPublish)
    id("dev.sergiobelda.gradle.spotless")
}

group = "dev.sergiobelda.navigation.compose.extended"
version = libs.versions.navigationComposeExtended.get()

android {
    namespace = "dev.sergiobelda.navigation.compose.extended.wear"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.androidWearMinSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    kotlin {
        jvmToolchain(libs.versions.jdk.get().toInt())
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.navigationComposeExtended)
    implementation(libs.androidx.wearCompose.navigation)
}

mavenPublishing {
    publishToMavenCentral(true)

    signAllPublications()
}
