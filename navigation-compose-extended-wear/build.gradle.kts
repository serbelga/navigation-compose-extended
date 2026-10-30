plugins {
    alias(deps.plugins.android.library)
    alias(deps.plugins.jetbrains.dokka)
    alias(deps.plugins.jetbrains.kotlin.composeCompiler)
    alias(deps.plugins.vanniktech.mavenpublish)
    alias(deps.plugins.sergiobelda.convention.spotless)
}

group = "dev.sergiobelda.navigation.compose.extended"
version = libs.versions.navigationComposeExtended.get()

android {
    namespace = "dev.sergiobelda.navigation.compose.extended.wear"
    compileSdk = deps.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = deps.versions.android.wear.minSdk.get().toInt()

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
    implementation(deps.androidx.wear.compose.navigation)
}

mavenPublishing {
    publishToMavenCentral(true)

    signAllPublications()
}
