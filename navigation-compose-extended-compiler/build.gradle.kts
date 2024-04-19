import com.vanniktech.maven.publish.SonatypeHost

plugins {
    kotlin("jvm")
    alias(libs.plugins.ksp)
    alias(libs.plugins.dokka)
    alias(libs.plugins.vanniktechMavenPublish)
    id("dev.sergiobelda.gradle.spotless")
}

group = "dev.sergiobelda.navigation.compose.extended"
version = libs.versions.navigationComposeExtended.get()

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

dependencies {
    implementation(libs.ksp.symbolProcessingApi)
    implementation(libs.squareup.kotlinpoet)
    implementation(libs.squareup.kotlinpoetKsp)
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.S01, true)

    signAllPublications()
}
