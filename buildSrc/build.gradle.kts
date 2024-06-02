import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies{
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.gradle.build.tools)
    implementation(libs.javapoet)
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "17"
}


gradlePlugin{
    plugins{
        register("androidApplicationConvention") {
            id = "growth-space.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibraryComposeConvention") {
            id = "growth-space.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidHiltConvention") {
            id = "growth-space.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("jvmLibraryConvention") {
            id = "growth-space.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("androidLibraryConvention") {
            id = "growth-space.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeatureConvention") {
            id = "growth-space.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidApplicationComposeConvention") {
            id = "growth-space.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibraryKtorConvention") {
            id = "growth-space.android.ktor"
            implementationClass = "AndroidLibraryKtorConventionPlugin"
        }
    }
}
