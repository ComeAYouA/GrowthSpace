plugins {
    id("growth-space.android.library")
    id("growth-space.android.hilt")
}

android {
    namespace = "comeayoua.growthspace.core.data"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:datastore"))
    implementation(project(":core:database"))
    implementation(project(":core:network"))

    implementation(libs.androidx.work.ktx)
}