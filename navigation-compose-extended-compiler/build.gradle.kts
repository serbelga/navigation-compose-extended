plugins {
    kotlin("jvm")
    alias(libs.plugins.ksp)
    alias(libs.plugins.dokka)
    id("dev.sergiobelda.gradle.spotless")
}

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
