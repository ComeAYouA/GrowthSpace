import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryKtorConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            dependencies {
                add(
                    "implementation",
                    versionCatalog.findLibrary("ktor-client-android").get()
                )
                add(
                    "implementation",
                    versionCatalog.findLibrary("ktor-utils").get()
                )
                add(
                    "implementation",
                    versionCatalog.findLibrary("ktor-core").get()
                )
            }
        }
    }

}