plugins {
    id("growth-space.android.library")
    id("growth-space.android.hilt")
}

android {
    namespace = "comeayoua.growthspace.core.data"
}

dependencies {
    implementation(project(":core:datastore"))
}