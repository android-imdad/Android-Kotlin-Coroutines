package lk.spacewa.coroutines.di.module

import android.content.Context
import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import lk.spacewa.coroutines.R
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * Created by Imdad on 7/11/2020.
 */

@Module
@InstallIn(ApplicationComponent::class)
class ApolloModule {

    @Singleton
    @Provides
    fun providePokemonAPI(okHttpClient: OkHttpClient,@ApplicationContext context: Context): ApolloClient {
        return ApolloClient.builder()
                .serverUrl(context.getString(R.string.base_api_url))
                .okHttpClient(okHttpClient)
                .build()
    }
}