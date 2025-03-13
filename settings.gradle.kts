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
        maven {
            url = uri("https://jitpack.io") // JitPack repository
        }
        maven {
            url = uri("https://phonepe.mycloudrepo.io/public/repositories/phonepe-intentsdk-android") // PhonePe repository
        }
    }
}

rootProject.name = "Agro Product demo"
include(":app")
