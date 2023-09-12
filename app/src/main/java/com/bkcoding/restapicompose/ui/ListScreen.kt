package com.bkcoding.restapicompose.ui
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.bkcoding.restapicompose.remotedata.Emp
import com.bkcoding.restapicompose.viewmodel.DummyViewModel

@Composable
fun ListScreen(viewModel: DummyViewModel = hiltViewModel()) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getAllEmployees()
    }
    EmployeeList()
}


@Composable
fun ItemDesign(data: Emp) {
    Card() {
        Column {
            Text(text = "Id : ${data.id}")
            Log.d("ItemDesign", "${data.employee_name}")
            Text(text = "Age : ${data.employee_age}")
            Text(text = "Name: ${data.employee_name}")
            Text(text = "Salary: ${data.employee_salary}")
        }
    }
}


@Composable
fun EmployeeList() {
    val viewModel: DummyViewModel = hiltViewModel()
    val empList = viewModel.empData.value ?: return
    LazyColumn() {
        items(empList.data) {
            ItemDesign(data = it)
        }
    }

}
