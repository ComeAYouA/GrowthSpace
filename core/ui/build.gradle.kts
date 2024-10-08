plugins {
    id("growth-space.android.library")
    id("growth-space.android.library.compose")
}
android {
    namespace = "comeayoua.growthspace.core.ui"
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

}