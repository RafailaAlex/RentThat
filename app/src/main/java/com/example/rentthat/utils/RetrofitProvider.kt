package com.example.rentthat.utils

import android.content.Context
import com.example.rentthat.R
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    private var retrofit: Retrofit? = null

    private fun getInstance(context: Context): Retrofit {
        if (retrofit != null) {
            return retrofit!!
        }
        synchronized(this) {
            return if (retrofit != null) {
                retrofit!!
            } else {
                val gson = GsonBuilder()
                    .setLenient()
                    .create()

                retrofit = Retrofit.Builder()
                    .baseUrl(context.resources.getString(R.string.api_server_staging))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

                retrofit!!
            }
        }
    }

    fun <T> createService(context: Context, service: Class<T>): T {
        return getInstance(
            context
        ).create(service)
    }
}