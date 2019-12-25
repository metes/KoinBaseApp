package com.base.model.retrofit.request

data class RenewPassRequest(
    val ConfirmPassword: String,
    val Password: String,
    val Token: String,
    val UserId: String
)