package comeayoua.growthspace.network

import comeayoua.growthspace.core.auth.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.composeAuth
import io.github.jan.supabase.compose.auth.googleNativeLogin
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.FlowType
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

@InstallIn(SingletonComponent::class)
@Module
interface NetworkModule {

    companion object{
        @Provides
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
        fun provideSupabaseComposeAuth(client: SupabaseClient): ComposeAuth = client.composeAuth

        @Provides
        fun provideSupabaseAuth(client: SupabaseClient): Auth = client.auth
    }
}