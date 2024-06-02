import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

val apiKey: String = gradleLocalProperties(rootDir).getProperty("supabaseApiKey")
val url: String = gradleLocalProperties(rootDir).getProperty("supabaseUrl")

plugins {
    id("growth-space.android.library")
    id("growth-space.android.hilt")
    id("growth-space.android.ktor")
}

android {
    namespace = "comeayoua.growthspace.core.data"
}

dependencies {

}