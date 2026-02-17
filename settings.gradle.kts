enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    includeBuild("build-logic")

    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MyCourse"
include(":app")
include(":core:data")
include(":core:model")
include(":core:navigation")
include(":core:network")
include(":core:database")
include(":core:designsystem")
include(":core:domain")

include(":feature:home")
include(":feature:details")
include(":feature:signIn")
include(":feature:signUp")
include(":feature:favorites")
include(":feature:user")