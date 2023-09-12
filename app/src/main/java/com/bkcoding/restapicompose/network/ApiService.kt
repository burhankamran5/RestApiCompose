package com.bkcoding.restapicompose.network

import com.bkcoding.restapicompose.remotedata.Emp
import com.bkcoding.restapicompose.remotedata.EmployeData
import com.bkcoding.restapicompose.remotedata.RequestData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("create")
    suspend fun createEmployee(
        @Body employeeData: RequestData
    ): Response<Emp>

    @GET("employees")
    suspend fun getAllEmployees(): Response<EmployeData>
}