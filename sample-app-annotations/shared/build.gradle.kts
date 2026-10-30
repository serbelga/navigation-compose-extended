import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("multiplatform")
    alias(deps.plugins.android.kotlinMultiplatformLibrary)
    alias(deps.plugins.google.ksp)
    alias(deps.plugins.jetbrains.compose)
    alias(deps.plugins.jetbrains.kotlin.composeCompiler)
    alias(deps.plugins.sergiobelda.convention.spotless)
}

kotlin {
    android {
        namespace = "dev.sergiobelda.navigation.compose.extended.sample.annotations"
        compileSdk = deps.versions.android.compileSdk.get().toInt()
        minSdk = deps.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    jvm()
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "sample_app"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.navigationComposeExtended)
            implementation(projects.navigationComposeExtendedAnnotation)

            implementation(deps.jetbrains.androidx.navigation.compose)
            implementation(deps.jetbrains.compose.foundation)
            implementation(deps.jetbrains.compose.material3)
            implementation(deps.jetbrains.compose.runtime)
            implementation(deps.jetbrains.compose.ui)
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", projects.navigationComposeExtendedCompiler)
}

// Workaround for KSP only in Common Main.
// https://github.com/google/ksp/issues/567
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<*>>().all {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

kotlin.sourceSets.commonMain {
    kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
}
