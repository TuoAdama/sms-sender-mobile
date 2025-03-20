package com.example.sms_sender.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.sms_sender.ui.screen.home.HomeScreen
import com.example.sms_sender.ui.screen.setting.SettingScreen
import com.example.sms_sender.ui.screen.setting.SettingViewModel


object AppRoute{
    const val HOME = "home"
    const val SETTING = "setting"
}



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
            startDestination = AppRoute.HOME,
            route = "onboarding"
        ) {
            composable(route = AppRoute.HOME) {
                HomeScreen(
                    navigateToSettingScreen = { navController.navigate(AppRoute.SETTING) },
                    settingViewModel = settingViewModel
                )
            }
            composable(route = AppRoute.SETTING){
                SettingScreen(
                    settingViewModel = settingViewModel,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}


@Composable
fun SmsSenderApp(navController: NavHostController = rememberNavController()){
    NavigationGraph(navController)
}