package comeayoua.growthspace.network.di

import comeayoua.growthspace.core.auth.BuildConfig
import comeayoua.growthspace.network.ProjectsApi
import comeayoua.growthspace.network.api.ProjectsApiImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal interface NetworkModule {

    @Binds
    fun bind_ProjectsApiImpl_toProjectsApi(input: ProjectsApiImpl): ProjectsApi

    companion object{
        @Provides
        @Singleton
        fun provideSupabaseClient(): SupabaseClient {
            return createSupabaseClient(
                supabaseUrl = BuildConfig.url,
                supabaseKey = BuildConfig.apiKey
            ) {
                install(Auth) {
                    scheme = "app"
                    host = "supabase.com"
                }
                install(Postgrest)
                install(Storage)
            }
        }

        @Provides
        @Singleton
        fun provideSupabaseAuth(client: SupabaseClient): Auth = client.auth

        @Provides
        @Singleton
        fun provideSupabaseDatabase(client: SupabaseClient): Postgrest {
            return client.postgrest
        }

        @Provides
        @Singleton
        fun provideSupabaseStorage(client: SupabaseClient): Storage {
            return client.storage
        }
    }
}