package com.base.network

import com.base.BuildConfig
import com.base.commons.SharedPrefHelper
import com.base.model.retrofit.request.ForgotPassRequest
import com.base.model.retrofit.request.LoginRequest
import com.base.model.retrofit.request.RenewPassRequest
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient : KoinComponent {

    private val sharedPrefHelper: SharedPrefHelper by inject()

    private val authToken: String
        get() {
            // TODO
            return sharedPrefHelper.loadToken() ?: ""
        }

    private val stethoInterceptor: StethoInterceptor? by lazy {
        if (BuildConfig.DEBUG) StethoInterceptor() else null
    }

    private val retrofitClient: APIInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(APIInterface::class.java)
    }
    private val okHttpClient: OkHttpClient
        get() {
            val clientBuilder = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val builder = chain.request().newBuilder()
                    builder.addHeader("Authorization", "Bearer $authToken")
                    builder.addHeader("Content-Type", "application/json")
                    chain.proceed(builder.build())
                }
            stethoInterceptor?.let { clientBuilder.addNetworkInterceptor(it) }
            return clientBuilder.build()
        }

    private val retrofitAuthClient: APIInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(okHttpAuthClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(APIInterface::class.java)
    }
    private val okHttpAuthClient: OkHttpClient
        get() {
            val clientBuilder = OkHttpClient.Builder().addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.addHeader("Content-Type", "application/x-www-form-urlencoded")
                chain.proceed(builder.build())
            }
            stethoInterceptor?.let { clientBuilder.addNetworkInterceptor(it) }
            return clientBuilder.build()
        }


    suspend fun loginClient(request: LoginRequest) =
        retrofitAuthClient.login(request.username, request.password, request.grantType)
    suspend fun forgotPassClient(request: ForgotPassRequest) = retrofitClient.forgotPass(request)
    suspend fun renewPassClient(request: RenewPassRequest) = retrofitClient.renewPass(request)
}



