plugins {
    id ("growth-space.android.application")
    id ("growth-space.android.application.compose")
    id ("growth-space.android.hilt")
}

android {
    namespace = "comeayoua.growthspace"
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.junit)
    implementation(libs.androidx.test.ext.junit)
    implementation(libs.androidx.splashscreen)


    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":feature:login"))
    implementation(project(":feature:projects"))
    implementation(project(":feature:onboarding"))
}