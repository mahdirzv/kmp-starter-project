rootProject.name = "Kmpstarterproject"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include(":composeApp")
include(":shared")
include(":kmp:auth:shared")
include(":kmp:auth:composeApp")
include(":kmp:room_data:shared")
include(":kmp:ui_theme:composeApp")

project(":kmp:auth:shared").projectDir = file("kmp/auth/shared")
project(":kmp:auth:composeApp").projectDir = file("kmp/auth/composeApp")
project(":kmp:room_data:shared").projectDir = file("kmp/room_data/shared")
project(":kmp:ui_theme:composeApp").projectDir = file("kmp/ui_theme/composeApp")