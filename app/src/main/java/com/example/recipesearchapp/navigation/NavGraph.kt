package com.example.recipesearchapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.recipesearchapp.bottomNavigationBar.BottomNavigationItems
import com.example.recipesearchapp.screens.Dashboard
import com.example.recipesearchapp.screens.FavouriteRecipeDetailedScreen
import com.example.recipesearchapp.screens.FavouriteRecipeView
import com.example.recipesearchapp.screens.RecipeView
import com.example.recipesearchapp.screens.SearchScreen
import com.example.recipesearchapp.screens.SignInScreen
import com.example.recipesearchapp.viewmodel.SharedViewModel

@Composable
fun SetupNavGraph(navController: NavHostController, onBottomBarVisibilityChanged: (Boolean) -> Unit) {
    val sharedViewModel : SharedViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.SignInScreen.route
    ) {
        composable(route = Screen.SignInScreen.route) {
            onBottomBarVisibilityChanged(false)
            SignInScreen(navController = navController)
        }

        composable(route = BottomNavigationItems.Dashboard.route) {
            onBottomBarVisibilityChanged(true)
            Dashboard(navController = navController, sharedViewModel = sharedViewModel)
        }

        composable(route = BottomNavigationItems.Favourite.route) {
            onBottomBarVisibilityChanged(true)
            FavouriteRecipeView(navController = navController, sharedViewModel = sharedViewModel)
        }

        composable(route = Screen.RecipeView.route) {
            onBottomBarVisibilityChanged(false)
            RecipeView(sharedViewModel = sharedViewModel)
        }

        composable(route = Screen.FavouriteRecipeDetailedScreen.route) {
            onBottomBarVisibilityChanged(false)
            FavouriteRecipeDetailedScreen(sharedViewModel = sharedViewModel)
        }

        composable(route = Screen.SearchScreen.route) {
            onBottomBarVisibilityChanged(false)
            SearchScreen(navController = navController)
        }
    }
}