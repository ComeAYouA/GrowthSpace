plugins {
    id("growth-space.android.feature")
    id("growth-space.android.hilt")
    id("growth-space.android.library.compose")
}

android {
    namespace = "comeayoua.growthspace.feature.login"
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(project(":core:datastore"))

    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
}