package com.base.network

import com.base.model.retrofit.request.ForgotPassRequest
import com.base.model.retrofit.request.RenewPassRequest
import com.base.model.retrofit.response.GenericResponse
import com.base.model.retrofit.response._loginAuth.LoginResponse
import com.base.model.retrofit.response.forgotPassword.ForgotPasswordResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIInterface {

    @POST("/token")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grant_type: String
    ): LoginResponse


    @POST("/api/Account/ForgotPassword")
    suspend fun forgotPass(@Body request: ForgotPassRequest): GenericResponse<ForgotPasswordResponse>


    @POST("/api/Account/ResetPassword")
    suspend fun renewPass(@Body request: RenewPassRequest): GenericResponse<String>
}
