package com.startdt.android.uploadfile.worker.http

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetBox(var url:String): HttpLoggingInterceptor.Logger {
    val DEFAULT_TIME_OUT = 3L
    val DEFAULT_READ_TIME_OUT = 3L

    private val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor(this)
    private val retrofit: Retrofit

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
            .build()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    fun <T> getService(service: Class<T>): T {
        return retrofit.create(service)
    }

    override fun log(message: String?) {
        print(message+"\n")
    }
}