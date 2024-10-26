import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationComposeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            androidApp().apply {
                buildFeatures {
                    compose = true
                }
            }

            dependencies {
                add("implementation", versionCatalog.findLibrary("compose-material3").get())
                add("implementation", versionCatalog.findLibrary("compose-ui-tooling").get())
                add("debugImplementation", versionCatalog.findLibrary("compose-ui-tooling-preview").get())
                add("implementation", versionCatalog.findLibrary("androidx-material3-windowSizeClass").get())
                add("implementation", versionCatalog.findLibrary("activity-compose").get())
                add("implementation", versionCatalog.findLibrary("compose-navigation").get())
            }
        }
    }
}