// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories{
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.hilt.agp)
    }
}

plugins {
    alias(libs.plugins.hiltPlugin) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.compose.compiler) apply false
}