plugins {
    kotlin("multiplatform")
    alias(deps.plugins.google.ksp)
    alias(deps.plugins.jetbrains.dokka)
    alias(deps.plugins.vanniktech.mavenpublish)
    alias(deps.plugins.sergiobelda.convention.spotless)
}

group = "dev.sergiobelda.navigation.compose.extended"
version = libs.versions.navigationComposeExtended.get()

kotlin {
    jvm {
        compilations.all {
            kotlin {
                jvmToolchain(libs.versions.jdk.get().toInt())
            }
        }
    }

    sourceSets {
        jvmMain {
            dependencies {
                implementation(deps.google.ksp.symbolProcessingApi)
                implementation(deps.squareup.kotlinpoet)
                implementation(deps.squareup.kotlinpoetKsp)
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
