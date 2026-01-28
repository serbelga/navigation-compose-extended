plugins {
    alias(libs.plugins.android.kotlinMultiplatformLibrary) apply false
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.vanniktechMavenPublish) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
}

dependencies {
    dokka(projects.navigationComposeExtended)
    dokka(projects.navigationComposeExtendedAnnotation)
    dokka(projects.navigationComposeExtendedCompiler)
    dokka(projects.navigationComposeExtendedWear)
}
