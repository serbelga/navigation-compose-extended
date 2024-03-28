import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.androidLibrary)
    kotlin("android")
    id("dev.sergiobelda.gradle.spotless")
    alias(libs.plugins.dokka)
    alias(libs.plugins.vanniktechMavenPublish)
}

group = "dev.sergiobelda.navigation.compose.extended"
version = libs.versions.navigationComposeExtended.get()

android {
    namespace = "dev.sergiobelda.navigation.compose.extended"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        jvmToolchain(17)
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {
    implementation(libs.androidx.navigation.compose)
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.S01, true)

    signAllPublications()
}
