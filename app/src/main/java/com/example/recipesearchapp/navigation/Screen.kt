package com.example.recipesearchapp.navigation

sealed class Screen (val route: String) {
    object SignInScreen: Screen("signinscreen")
    object RecipeView: Screen("recipeview")
    object FavouriteRecipeDetailedScreen: Screen("favouriterecipedetailedscreen")
    object SearchScreen: Screen("searchscreen")
}