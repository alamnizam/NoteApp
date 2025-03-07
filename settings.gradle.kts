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
include(":notes:note_details:presentation")
include(":notes:note_search:presentation")
include(":notes:note_list:data")
include(":notes:note_list:domain")
include(":notes:note_list:presentation")
include(":navigation")
include(":authentication:navigation")
