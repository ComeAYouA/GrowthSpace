plugins {
    id("growth-space.android.library")
    id("growth-space.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "comeayoua.growthspace.core.database"
}

dependencies {
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.jetbrains.kotlinx.datetime)

    implementation(project(":core:model"))
}