package com.example.sms_sender.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sms_sender.ui.screen.home.HomeScreen
import com.example.sms_sender.ui.screen.home.HomeScreenDestination
import com.example.sms_sender.ui.screen.setting.SettingScreen
import com.example.sms_sender.ui.screen.setting.SettingScreenDestination
import com.example.sms_sender.ui.screen.setting.SettingViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeScreenDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeScreenDestination.route) {
            HomeScreen(
                navigateToSettingScreen = { navController.navigate(SettingScreenDestination.route) },
            )
        }
        composable(route = SettingScreenDestination.route){
            SettingScreen(
                settingViewModel = viewModel(factory = SettingViewModel.Factory),
                onStopService = {},
                onStartService = {}
            )
        }
    }
}


@Composable
fun SmsSenderApp(navController: NavHostController = rememberNavController()){
    NavigationGraph(navController)
}