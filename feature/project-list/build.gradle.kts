plugins {
    id("growth-space.android.feature")
    id("growth-space.android.hilt")
    id("growth-space.android.library.compose")
}

android {
    namespace = "comeayoua.growthspace.feature.project.list"
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.jetbrains.kotlinx.datetime)

    implementation(project(":sync"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:ui"))
}