package com.bkcoding.restapicompose.common

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class BaseRepository {
    suspend fun <T : Any> safeApiRequest(call: suspend () -> Response<T>): Result<T> {
        val response = call.invoke()
        if (response.isSuccessful) {
            return Result.success(response.body()!!)
        } else {
            val responseErr = response.errorBody()?.string()
            val message = StringBuilder()
            responseErr.let {
                try {
                    message.append(it?.let { it1 -> JSONObject(it1).getString("error") })
                } catch (_: JSONException) {
                }
            }
            return Result.failure(Exception(message.toString()))
        }
    }



}