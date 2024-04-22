import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.androidLibrary)
    kotlin("android")
    alias(libs.plugins.dokka)
    alias(libs.plugins.vanniktechMavenPublish)
    id("dev.sergiobelda.gradle.spotless")
}

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
}

dependencies {
    implementation(projects.navigationComposeExtended)
    implementation(libs.androidx.wearCompose.navigation)
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.S01, true)

    signAllPublications()
}
