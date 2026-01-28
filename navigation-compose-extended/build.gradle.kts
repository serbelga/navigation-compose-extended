import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("multiplatform")
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.android.kotlinMultiplatformLibrary)
    id("dev.sergiobelda.gradle.spotless")
    alias(libs.plugins.dokka)
    alias(libs.plugins.vanniktechMavenPublish)
}

group = "dev.sergiobelda.navigation.compose.extended"
version = libs.versions.navigationComposeExtended.get()

kotlin {
    androidLibrary {
        namespace = "dev.sergiobelda.navigation.compose.extended"
        compileSdk = libs.versions.androidCompileSdk.get().toInt()
        minSdk = libs.versions.androidMinSdk.get().toInt()

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.jetbrains.androidx.navigation.compose)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

mavenPublishing {
    publishToMavenCentral(true)

    signAllPublications()
}
