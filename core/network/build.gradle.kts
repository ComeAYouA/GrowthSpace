import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

val apiKey: String = gradleLocalProperties(rootDir).getProperty("supabaseApiKey")
val url: String = gradleLocalProperties(rootDir).getProperty("supabaseUrl")
val clientId: String = gradleLocalProperties(rootDir).getProperty("OAuthClientId")

plugins {
    id("growth-space.android.library")
    id("growth-space.android.hilt")
    id("growth-space.android.ktor")
}

android {
    namespace = "comeayoua.growthspace.core.auth"

    buildFeatures {
        buildConfig = true
    }

    buildTypes{
        getByName("debug"){
            buildConfigField("String", "apiKey", apiKey)
            buildConfigField("String", "url", url)
            buildConfigField("String", "clientId", clientId)
        }
    }
}

dependencies {
    implementation(libs.supabase.gotrue)
    implementation(libs.supabase.postgrest)
    implementation(libs.supabase.storage)
    implementation(libs.supabase.auth)
}