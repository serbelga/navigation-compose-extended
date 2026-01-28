plugins {
    kotlin("multiplatform")
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.jetbrains.dokka)
    alias(libs.plugins.vanniktechMavenPublish)
    id("dev.sergiobelda.gradle.spotless")
}

group = "dev.sergiobelda.navigation.compose.extended"
version = libs.versions.navigationComposeExtended.get()

kotlin {
    jvm {
        compilations.all {
            kotlin {
                jvmToolchain(17)
            }
        }
    }

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(libs.google.ksp.symbolProcessingApi)
                implementation(libs.squareup.kotlinpoet)
                implementation(libs.squareup.kotlinpoetKsp)
                implementation(projects.navigationComposeExtendedAnnotation)
            }
            kotlin.srcDir("src/main/kotlin")
            resources.srcDir("src/main/resources")
        }
    }
}

mavenPublishing {
    publishToMavenCentral(true)

    signAllPublications()
}
