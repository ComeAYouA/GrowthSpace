plugins {
    id("growth-space.android.library")
    id("growth-space.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "comeayoua.growthspace.core.datastore"
}

dependencies {
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore)
    implementation(libs.kotlinx.serialization.json)

    implementation(project(":core:model"))
}