package com.example.sms_sender.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
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
    val settingViewModel: SettingViewModel = viewModel(factory = SettingViewModel.Factory)

    NavHost(
        navController = navController,
        startDestination = "onboarding",
        modifier = modifier
    ) {

        navigation(
            startDestination = HomeScreenDestination.route,
            route = "onboarding"
        ) {
            composable(route = HomeScreenDestination.route) {
                HomeScreen(
                    navigateToSettingScreen = { navController.navigate(SettingScreenDestination.route) },
                    settingViewModel = settingViewModel
                )
            }
            composable(route = SettingScreenDestination.route){
                SettingScreen(
                    settingViewModel = settingViewModel
                )
            }
        }
    }
}


@Composable
fun SmsSenderApp(navController: NavHostController = rememberNavController()){
    NavigationGraph(navController)
}


@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel(factory = SettingViewModel.Factory)
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}