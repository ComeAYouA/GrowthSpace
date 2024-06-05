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
    implementation(libs.supabase.gotrue)
    implementation(libs.supabase.auth)
    implementation(libs.coil.compose)


    implementation(project(":core:data"))
    implementation(project(":core:network"))
    implementation(project(":core:ui"))
}