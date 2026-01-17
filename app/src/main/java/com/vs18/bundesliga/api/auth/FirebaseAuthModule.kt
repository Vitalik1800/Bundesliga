package com.vs18.bundesliga.api.auth

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object FirebaseAuthModule {

    private const val API_KEY = "AIzaSyCshbVhzZzADE5vtk9jODjTAaI6NK2ACxk"
    private const val BASE_URL = "https://identitytoolkit.googleapis.com/"

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val api: FirebaseAuthApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(FirebaseAuthApi::class.java)

    fun apiKey() = API_KEY
}