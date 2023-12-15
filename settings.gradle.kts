pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // QR Code Scanner
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "GearTracker"
include(":app")
 