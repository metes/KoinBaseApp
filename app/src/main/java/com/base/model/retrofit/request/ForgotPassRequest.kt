package com.base.model.retrofit.request

import com.google.gson.annotations.SerializedName

data class ForgotPassRequest(
    @SerializedName("Email")
    val eMail: String? = ""
)