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
    @Suppress("UnstableApiUsage")
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    @Suppress("UnstableApiUsage")
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Notes"
include(":app")
include(":authentication:login:data")
include(":authentication:login:domain")
include(":authentication:login:presentation")
include(":authentication:naviagtion")
include(":authentication:registration:data")
include(":authentication:registration:domain")
include(":authentication:registration:presentation")
include(":common")
include(":notes:add_note:data")
include(":notes:add_note:domain")
include(":notes:add_note:presentation")
include(":notes:edit_note:data")
include(":notes:edit_note:domain")
include(":notes:edit_note:presentation")
include(":notes:navigation")
include(":notes:note_details:presentation")
