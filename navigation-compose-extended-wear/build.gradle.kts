plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.dokka)
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
        consumerProguardFiles("consumer-rules.pro")
    }
    kotlin {
        jvmToolchain(17)
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
