package com.greektrust.common.ui

sealed interface UIState<out T> {

    object Loading : UIState<Nothing>
    data class Success<T>(val data:T) : UIState<T>
    data class Failure(val msg: String) : UIState<Nothing>

}