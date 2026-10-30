plugins {
    alias(deps.plugins.android.application) apply false
    alias(deps.plugins.android.kotlinMultiplatformLibrary) apply false
    alias(deps.plugins.android.library) apply false
    alias(deps.plugins.google.ksp) apply false
    alias(deps.plugins.jetbrains.dokka)
    alias(deps.plugins.jetbrains.kotlin.composeCompiler) apply false
    alias(deps.plugins.jetbrains.kotlin.jvm) apply false
    alias(deps.plugins.vanniktech.mavenpublish) apply false
    alias(deps.plugins.spotless) apply false
}

dependencies {
    dokka(projects.navigationComposeExtended)
    dokka(projects.navigationComposeExtendedAnnotation)
    dokka(projects.navigationComposeExtendedCompiler)
    dokka(projects.navigationComposeExtendedWear)
}
