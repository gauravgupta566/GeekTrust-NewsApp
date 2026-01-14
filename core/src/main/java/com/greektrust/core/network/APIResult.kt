package com.greektrust.core.network

sealed interface APIResult<out T> {

    object Loading : APIResult<Nothing>
    data class Success<T>(val data:T) : APIResult<T>
    data class Error<APIError>(val error: APIError) : APIResult<Nothing>
}