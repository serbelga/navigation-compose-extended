import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    id("dev.sergiobelda.gradle.spotless")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

dependencies {
    implementation(projects.sampleApp.shared)

    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "dev.sergiobelda.navigation.compose.extended.sample.app.MainApplicationKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
        }
    }
}
