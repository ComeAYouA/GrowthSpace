import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryComposeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            androidLib().apply {
                buildFeatures {
                    compose = true
                }
            }

            dependencies {
                add("implementation", versionCatalog.findLibrary("hilt-navigation-compose").get())
                add("implementation", versionCatalog.findLibrary("compose-material3").get())
                add("implementation", versionCatalog.findLibrary("compose-ui-tooling").get())
                add("debugImplementation", versionCatalog.findLibrary("compose-ui-tooling-preview").get())
            }
        }
    }

}