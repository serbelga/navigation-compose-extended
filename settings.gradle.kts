pluginManagement {
    includeBuild("gradle/build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "navigation-compose-extended-root"

include(":navigation-compose-extended")
include(":navigation-compose-extended-annotation")
include(":navigation-compose-extended-compiler")
include(":navigation-compose-extended-wear")

include(":sample-app-annotations:android")
include(":sample-app-annotations:desktop")
include(":sample-app-annotations:shared")
include(":sample-app:android")
include(":sample-app:desktop")
include(":sample-app:shared")
