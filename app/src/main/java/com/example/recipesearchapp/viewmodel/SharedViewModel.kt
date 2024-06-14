package com.example.recipesearchapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.recipesearchapp.models.RandomRecipeModel.Recipe
import com.example.recipesearchapp.room.model.FavouriteRecipe

class SharedViewModel: ViewModel() {

    var recipeState = mutableStateOf<Recipe?>(null)
        private set

    var favRecipeState = mutableStateOf<FavouriteRecipe?>(null)
        private set

    fun addRecipe(newRecipe: Recipe) {
        recipeState.value = newRecipe
    }

    fun addFavRecipe(newFavRecipe: FavouriteRecipe) {
        favRecipeState.value = newFavRecipe
    }
}