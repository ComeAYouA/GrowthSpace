import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("growth-space.android.library")
    id("growth-space.android.hilt")
    id("growth-space.android.ktor")
}

android {
    namespace = "comeayoua.growthspace.core.domain"
}

dependencies {
    implementation(libs.supabase.gotrue)
    implementation(libs.supabase.auth)
    implementation(project(":core:data"))

    implementation(project(":core:network"))
    implementation(project(":core:model"))
}