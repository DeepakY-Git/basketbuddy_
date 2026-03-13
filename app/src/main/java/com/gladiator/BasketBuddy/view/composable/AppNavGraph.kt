package com.gladiator.BasketBuddy.view.composable

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gladiator.BasketBuddy.model.Screen

@Composable
fun AppNavGraph(navController: NavHostController){

    NavHost(
        navController=navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route){
            SplashScreen(onNavigate = {navController.navigate(Screen.SignUp.route){
                popUpTo(Screen.Splash.route){
                    inclusive=true
                }
                launchSingleTop=true
            } })
        }
        composable(Screen.SignUp.route){
            SignUpScreen(navController,viewModel(), onSignUpSuccess = {
                navController.navigate(Screen.Login.route){
                    popUpTo(Screen.SignUp.route){
                        inclusive=true
                    }
                    launchSingleTop=true
                }
            })
        }

        composable(Screen.Login.route){
            LoginScreen(navController,viewModel(), onLoginSuccess = {
                navController.navigate(Screen.Home.route){
                } })
        }

        composable(Screen.Home.route){
            HomeScreen(navController)
        }

        composable(Screen.Collaboration.route){
            Collaborations(hint = "search", onSearch = {}, navController = navController)
        }

        composable(Screen.ListDisplay.route){
            ListScreen(navController)
        }

        composable(Screen.ItemDisplay.route){
            ItemDisplayScreen(navController)
        }

        composable(Screen.AddList.route){
            AddListScreen(navController)
        }

        composable(Screen.AddItem.route){
            AddItemScreen(navController)
        }
        composable("summaryScreen"){
            PreviewSummary()
        }
    }
}