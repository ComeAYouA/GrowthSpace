plugins {
    id("growth-space.jvm.library")
    id("kotlinx-serialization")
}

dependencies{
    api(libs.jetbrains.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
}