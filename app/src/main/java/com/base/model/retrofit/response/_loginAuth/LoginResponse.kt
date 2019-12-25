package com.base.model.retrofit.response._loginAuth

data class LoginResponse(
    val access_token: String,
    val expires_in: Int,
    val token_type: String
)