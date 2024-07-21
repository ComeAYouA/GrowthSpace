plugins {
    id("growth-space.jvm.library")
    id("kotlinx-serialization")
}

dependencies{
    implementation(libs.jetbrains.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)

    implementation(project(":core:model"))
}