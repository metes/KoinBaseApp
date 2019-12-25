package com.base.model.retrofit.response

data class GenericResponse<T>(
    val `data`: T,
    val errorMessage: List<String>?,
    val isSuccess: Boolean,
    var statusCode: Int
)