package com.base.model.retrofit.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username")
    val username: String = "",
    @SerializedName("password")
    val password: String = "",
    @SerializedName("grant_type")
    val grantType: String = "password"
)