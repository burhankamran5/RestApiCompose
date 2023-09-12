package com.bkcoding.restapicompose.common

/**
 * Result class when loading data.
 */

sealed class ApiState<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : ApiState<T>(data = data)
    class Loading<T> : ApiState<T>()
    class Error<T>(message: String?) : ApiState<T>(message = message)
}