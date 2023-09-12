import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bkcoding.restapicompose.viewmodel.DummyViewModel
import com.bkcoding.restapicompose.viewmodel.StateHolder

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun DataEntryScreen(
    viewModel: DummyViewModel = hiltViewModel(),
    navController: NavController
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val state = viewModel.stateHolder.value
    Log.d("DataEntryScreen", "DataEntryScreen: ${viewModel.inputData.value.age.toString()}")
    if (state.error.isNotEmpty()) {
        Dialog(
            onCancel = { viewModel.stateHolder.value = StateHolder() },
            onRetry = { viewModel.createEmp() }
        )
    } else {
        if (state.success) {
            navController.navigate("ListScreen")
            viewModel.stateHolder.value = StateHolder()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.White, // Start color (White)
                                Color(0xFF00C8FF)  // End color (Light Blue)
                            ),
                        )
                    )
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Register Employee Form",
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 24.sp),
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                OutlinedTextField(
                    value = viewModel.inputData.value.name,
                    onValueChange = { newName ->
                        viewModel.inputData.value = viewModel.inputData.value.copy(name = newName)
                    },

                    label = { Text("Name") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { keyboardController?.show() }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = viewModel.inputData.value.age.toString(),
                    onValueChange = { newAge ->
                        viewModel.inputData.value =
                            viewModel.inputData.value.copy(age = newAge.toInt())
                    },
                    label = { Text("Age") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { keyboardController?.show() }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = viewModel.inputData.value.salary.toString(),
                    onValueChange = { salary ->
                        viewModel.inputData.value =
                            viewModel.inputData.value.copy(salary = salary.toInt())
                    },
                    label = { Text("Salary") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp)
                )


                Button(
                    onClick = { viewModel.createEmp() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(top = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF004080),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        if (state.isLoading) {
                            CircularProgressIndicator(modifier = Modifier.padding(9.dp))
                            "Loading"
                        } else "Done"
                    )

                }
            }
        }

    }
}


@Composable
fun Dialog(
    onCancel: () -> Unit,
    onRetry: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onCancel() },
        title = { Text(text = "Something went wrong!") },
        text = { },
        confirmButton = {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "",
                modifier = Modifier.clickable { onRetry() })
        },
        dismissButton = {
            Icon(
                imageVector = Icons.Outlined.Clear,
                contentDescription = "",
                modifier = Modifier.clickable { onCancel() })
        },
    )

}

