plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.kotlinMultiplatformLibrary) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.google.ksp) apply false
    alias(libs.plugins.jetbrains.dokka)
    alias(libs.plugins.jetbrains.kotlin.composeCompiler) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.vanniktechMavenPublish) apply false
}

dependencies {
    dokka(projects.navigationComposeExtended)
    dokka(projects.navigationComposeExtendedAnnotation)
    dokka(projects.navigationComposeExtendedCompiler)
    dokka(projects.navigationComposeExtendedWear)
}
