plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.vanniktechMavenPublish) apply false
}

dependencies {
    dokka(projects.navigationComposeExtended)
    dokka(projects.navigationComposeExtendedAnnotation)
    dokka(projects.navigationComposeExtendedCompiler)
    dokka(projects.navigationComposeExtendedWear)
}
