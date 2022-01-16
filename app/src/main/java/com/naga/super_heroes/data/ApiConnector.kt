/*
* Ismael Zavala Lopez
* 2021
* Maker Media Group
*/

package com.naga.super_heroes.data

import com.naga.super_heroes.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConnector() {

    fun <Api> retrofitBuilder(
        ApiInterface: Class<Api>,
        baseUrl: String,
        authToken: String? = null
    ) : Api {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        chain.proceed(chain.request().newBuilder().also {
                            it.addHeader("access_token", "$authToken")
                        }.build())
                    }
                    .also { client ->
                    /*if (BuildConfig.DEBUG) {
                        val loggin = HttpLoggingInterceptor()
                        loggin.setLevel(HttpLoggingInterceptor.Level.BODY)
                        client.addInterceptor(loggin)
                    }*/
                }.build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface)
    }

}