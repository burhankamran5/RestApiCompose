package com.bkcoding.navigation

import DataEntryScreen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bkcoding.restapicompose.ui.ListScreen
import com.bkcoding.restapicompose.viewmodel.DummyViewModel

@Composable
fun Navigation() {

    val navController = rememberNavController()
    val viewModel: DummyViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = "CreateEmployeeScreen"
    ) {
        composable("CreateEmployeeScreen") {
            DataEntryScreen(viewModel, navController)
        }

        composable("ListScreen") {
            ListScreen()
        }

    }
}