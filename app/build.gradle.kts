plugins {
    id ("growth-space.android.application")
    id ("growth-space.android.application.compose")
    id ("growth-space.android.hilt")
}

android {
    namespace = "comeayoua.growthspace"
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.junit)
    implementation(libs.androidx.test.ext.junit)
    implementation(libs.androidx.splashscreen)
    implementation(libs.androidx.work.ktx)


    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:ui"))
    implementation(project(":core:database"))
    implementation(project(":core:network"))
    implementation(project(":sync"))
    implementation(project(":feature:login"))
    implementation(project(":feature:project-list"))
    implementation(project(":feature:project-add"))
    implementation(project(":feature:onboarding"))
    implementation(libs.hilt.ext.work)
}