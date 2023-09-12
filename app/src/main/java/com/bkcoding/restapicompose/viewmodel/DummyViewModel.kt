package com.bkcoding.restapicompose.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bkcoding.restapicompose.common.ApiState
import com.bkcoding.restapicompose.remotedata.Emp
import com.bkcoding.restapicompose.remotedata.EmployeData
import com.bkcoding.restapicompose.repo.DummyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "DummyViewModel"

@HiltViewModel
class DummyViewModel @Inject constructor(private val dummyRepository: DummyRepository) :
    ViewModel() {

    var empData = mutableStateOf<EmployeData?>(null)
    val stateHolder = mutableStateOf(StateHolder())
    val inputData = mutableStateOf(InputData())

    private fun createEmployee() {
        viewModelScope.launch {
            dummyRepository.createEmployee(
                inputData.value.name,
                inputData.value.age,
                inputData.value.salary
            ).collect {
                when (it) {
                    is ApiState.Success -> {
                        stateHolder.value = StateHolder(data = it, success = true)
                    }

                    is ApiState.Loading -> {
                        stateHolder.value = StateHolder(isLoading = true)
                    }

                    is ApiState.Error -> {
                        stateHolder.value = StateHolder(error = it.message.toString())
                    }
                }
            }
        }
    }

    fun createEmp() {
        val inputDataValue = inputData.value
        if (inputDataValue.name.isEmpty() || inputDataValue.age.toString()
                .isEmpty() || inputDataValue.salary.toString().isEmpty()
        ) {
            stateHolder.value = StateHolder(error = "Please add data")
        } else {
            createEmployee()
        }
    }

    suspend fun getAllEmployees() = viewModelScope.launch {
        dummyRepository.getAllEmployees().collect {
            when (it) {
                is ApiState.Success -> {
                    it.data?.let { data->
                        empData.value = data
                    }
                }
                is ApiState.Loading ->{}
                is ApiState.Error ->{

                }
            }

        }

    }

}

data class StateHolder(
    var isLoading: Boolean = false,
    val error: String = "",
    val data: ApiState<Emp>? = null,
    val success: Boolean = false
)

data class InputData(
    var name: String = "",
    var age: Int = 0,
    var salary: Int = 0
)
