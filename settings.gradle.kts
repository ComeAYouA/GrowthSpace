pluginManagement {
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

rootProject.name = "GrowthSpace"
include(":app")
include(":core:datastore")
include(":core:network")
include(":feature:login")
include(":feature:projects")
include(":core:ui")
include(":feature:onboarding")
include(":core:domain")
include(":core:model")
include(":core:data")
