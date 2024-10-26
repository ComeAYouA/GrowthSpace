plugins {
    id("growth-space.android.library")
    id("growth-space.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "comeayoua.growthspace.sync"
}

dependencies {
    kapt(libs.hilt.ext.compiler)

    implementation(libs.androidx.tracing.ktx)
    implementation(libs.androidx.work.ktx)
    implementation(libs.hilt.ext.work)
    implementation(libs.kotlinx.serialization.json)

    implementation(project(":core:model"))
    implementation(project(":core:data"))
    implementation(libs.androidx.hilt.common)
}