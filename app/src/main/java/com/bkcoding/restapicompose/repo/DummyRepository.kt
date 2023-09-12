package com.bkcoding.restapicompose.repo

import com.bkcoding.restapicompose.common.ApiState
import com.bkcoding.restapicompose.common.BaseRepository
import com.bkcoding.restapicompose.network.ApiService
import com.bkcoding.restapicompose.remotedata.Emp
import com.bkcoding.restapicompose.remotedata.EmployeData
import com.bkcoding.restapicompose.remotedata.RequestData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DummyRepository @Inject constructor(private val apiService: ApiService) : BaseRepository() {

    suspend fun createEmployee(name: String, age: Int, salary: Int): Flow<ApiState<Emp>> = flow {
        emit(ApiState.Loading())
        try {
            emit(
                ApiState.Success(
                    safeApiRequest {
                        apiService.createEmployee(RequestData(name, age, salary))
                    }.getOrNull()
                )
            )
        } catch (e: Exception) {
            emit(ApiState.Error(e.message))
        }
    }

    suspend fun getAllEmployees(): Flow<ApiState<EmployeData>> = flow {
        emit(ApiState.Loading())
        try {
            ApiState.Success(
                safeApiRequest { apiService.getAllEmployees() }
            )
        } catch (e: Exception) {
            emit(ApiState.Error(e.message))
        }
    }


}

