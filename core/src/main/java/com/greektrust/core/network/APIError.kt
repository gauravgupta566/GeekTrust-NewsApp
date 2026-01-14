package com.greektrust.core.network

data class APIError(
    val error: String,
    val code: Int = 400
)