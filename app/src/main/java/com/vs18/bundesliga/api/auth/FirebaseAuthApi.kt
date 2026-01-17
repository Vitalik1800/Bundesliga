package com.vs18.bundesliga.api.auth

import com.vs18.bundesliga.model.auth.*
import com.vs18.bundesliga.remote.BaseResponse
import com.vs18.bundesliga.remote.ForgotPasswordRequest
import retrofit2.http.*

interface FirebaseAuthApi {

    @POST("v1/accounts:signInWithPassword")
    suspend fun login(
        @Query("key") apiKey: String,
        @Body body: AuthRequest
    ): AuthResponse

    @POST("v1/accounts:signUp")
    suspend fun register(
        @Query("key") apiKey: String,
        @Body body: AuthRequest
    ): AuthResponse

    @POST("auth/forgot-password")
    suspend fun forgotPassword(
        @Body request: ForgotPasswordRequest
    ): BaseResponse
}