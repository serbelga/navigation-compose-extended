import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("multiplatform")
    alias(deps.plugins.android.kotlinMultiplatformLibrary)
    alias(deps.plugins.jetbrains.compose)
    alias(deps.plugins.jetbrains.dokka)
    alias(deps.plugins.jetbrains.kotlin.composeCompiler)
    alias(deps.plugins.vanniktech.mavenpublish)
    alias(deps.plugins.sergiobelda.convention.spotless)
}

group = "dev.sergiobelda.navigation.compose.extended"
version = libs.versions.navigationComposeExtended.get()

kotlin {
    android {
        namespace = "dev.sergiobelda.navigation.compose.extended"
        compileSdk = deps.versions.android.compileSdk.get().toInt()
        minSdk = deps.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    jvm()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(deps.jetbrains.androidx.navigation.compose)
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
