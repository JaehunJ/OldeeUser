package com.oldee.user.network

import android.content.Context
import android.util.Log
import com.oldee.user.BuildConfig
import com.orhanobut.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OldeeApi {
    @Provides
    @Singleton
    fun provideHttpClient(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
//        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(OldeeService::class.java)

    @Provides
    @Singleton
    fun getOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val interceptor2 = NoConnectionInterceptor(context)
        val loggerInterceptor = HttpLoggingInterceptor(
            object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    try {
                        JSONObject(message)
                        Logger.t("json").json(message)
                    } catch (error: JSONException) {
                        if(message.isNotEmpty())
                            Log.d("PRETTY_LOGGER", message)
                    }
                }
            }
        ).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder().addInterceptor(loggerInterceptor).addInterceptor(interceptor2)
            .build()
    }
}