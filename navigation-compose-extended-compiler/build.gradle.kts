import com.vanniktech.maven.publish.SonatypeHost

plugins {
    kotlin("multiplatform")
    alias(libs.plugins.ksp)
    alias(libs.plugins.dokka)
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
                implementation(libs.ksp.symbolProcessingApi)
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
    publishToMavenCentral(SonatypeHost.S01, true)

    signAllPublications()
}
