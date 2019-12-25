package com.base.model.retrofit.response.forgotPassword

data class ForgotPasswordResponse(
    val token: String,
    val userid: String,
    val verifyCode: String
)