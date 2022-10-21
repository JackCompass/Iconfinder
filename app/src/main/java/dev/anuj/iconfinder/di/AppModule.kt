package dev.anuj.iconfinder.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.anuj.iconfinder.BuildConfig
import dev.anuj.iconfinder.network.ApiService
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .authenticator(object : Authenticator {
                        override fun authenticate(route: Route?, response: Response): Request? {
                            if (response.request.header("Authorization") != null) {
                                return null
                            }
                            return response.request.newBuilder().header("Authorization", "Bearer " + BuildConfig.API_KEY).build()
                        }
                    })
                    .build()
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return provideRetrofit().create(ApiService::class.java)
    }
}