import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("growth-space.android.library")
    id("growth-space.android.hilt")
}

android {
    namespace = "comeayoua.growthspace.core.datastore"
}

dependencies {
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore)
    implementation(libs.supabase.auth)
}