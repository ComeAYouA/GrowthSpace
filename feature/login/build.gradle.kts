import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

val clientId: String = gradleLocalProperties(rootDir).getProperty("OAuthClientId")

plugins {
    id("growth-space.android.feature")
    id("growth-space.android.hilt")
    id("growth-space.android.library.compose")
}

android {
    namespace = "comeayoua.growthspace.feature.login"

    buildFeatures {
        buildConfig = true
    }

    buildTypes{
        getByName("debug"){
            buildConfigField("String", "clientId", clientId)
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.supabase.auth)
    implementation(libs.coil.compose)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.playServices)
    implementation(libs.identity.googleId)
    implementation(project(":core:datastore"))


    implementation(project(":core:model"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
}