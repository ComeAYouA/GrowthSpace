plugins {
    id("growth-space.android.feature")
    id("growth-space.android.hilt")
    id("growth-space.android.library.compose")
}

android {
    namespace = "comeayoua.growthspace.feature.projects"
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.glide.compose)
    implementation(libs.supabase.gotrue)
    implementation(libs.supabase.auth)
    implementation(project(":core:datastore"))


    implementation(project(":core:network"))
}