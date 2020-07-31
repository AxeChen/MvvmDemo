package com.axe.libretrofit


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {

    private const val CONNECT_TIMEOUT = 10L
    private const val READ_TIMEOUT = 10L
    private const val WRITE_TIMEOUT = 10L

    /**
     * create Retrofit
     */
    private fun createService(url: String): Retrofit {
        // okHttpClientBuilder
        val okHttpClientBuilder = OkHttpClient().newBuilder().apply {
            val loggingInterceptor = HttpLoggingInterceptor()
            var level = HttpLoggingInterceptor.Level.NONE
            // 添加是否打开日志的标志
            loggingInterceptor.level = level
            addInterceptor(loggingInterceptor)
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        }

        return RetrofitBuild(
            url = url,
            client = okHttpClientBuilder.build(),
            gsonFactory = GsonConverterFactory.create()
        ).retrofit
    }

    /**
     * get ServiceApi
     */
    fun <T> getService(service: Class<T>, host: String): T {
        return createService(host)
            .create(service)
    }

    /**
     * create retrofit build
     */
    class RetrofitBuild(
        url: String,
        client: OkHttpClient,
        gsonFactory: GsonConverterFactory
    ) {
        val retrofit: Retrofit = Retrofit.Builder().apply {
            baseUrl(url)
            client(client)
            addConverterFactory(gsonFactory)
        }.build()
    }

}