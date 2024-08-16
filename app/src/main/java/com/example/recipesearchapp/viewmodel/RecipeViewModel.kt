package com.example.recipesearchapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesearchapp.data.remote.model.AutoCompleteRecipeModel.AutoCompleteRecipeSearchApiResponse
import com.example.recipesearchapp.data.remote.model.RandomRecipeModel.RandomRecipeApiResponse
import com.example.recipesearchapp.data.remote.model.SearchRecipeModel.SearchRecipeApiResponse
import com.example.recipesearchapp.data.room.model.FavouriteRecipe
import com.example.recipesearchapp.data.repository.RecipeRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipeViewModel @Inject constructor (private val repository: RecipeRepository): ViewModel() {

    val randomRecipesLiveData: LiveData<RandomRecipeApiResponse>
        get() = repository.randomRecipes
    val randomRecipesLoadingLiveData: LiveData<Boolean>
        get() = repository.randomRecipesLoading

    fun getRandomRecipes() {
        viewModelScope.launch {
            repository.getRandomRecipes()
        }
    }

    suspend fun getSingleRandomRecipe(): RandomRecipeApiResponse? {
        return repository.getSingleRandomRecipe()
    }

    val allRecipesLiveData: LiveData<RandomRecipeApiResponse>
        get() = repository.allRecipes
    val allRecipesLoadingLiveData: LiveData<Boolean>
        get() = repository.allRecipesLoading

    fun getAllRecipe() {
        viewModelScope.launch {
            repository.getAllRecipe()
        }
    }

    val autoCompleteRecipesLiveData: LiveData<List<AutoCompleteRecipeSearchApiResponse>>
        get() = repository.autoCompleteRecipes

    fun getAutoCompleteRecipe(query: String) {
        viewModelScope.launch {
            repository.getAutoCompleteRecipe(query)
        }
    }

    val searchRecipesLiveData: LiveData<List<SearchRecipeApiResponse>>
        get() = repository.searchRecipes

    fun getSearchedRecipes(ids: String) {
        viewModelScope.launch {
            repository.getSearchedRecipes(ids)
        }
    }

    fun addFavouriteRecipe(favRecipe: FavouriteRecipe) {
        viewModelScope.launch {
            repository.addFavouriteRecipe(favRecipe)
        }
    }

    fun deleteFavouriteRecipe(favRecipe: FavouriteRecipe) {
        viewModelScope.launch {
            repository.deleteFavouriteRecipe(favRecipe)
        }
    }

    val favouriteRecipesLiveData: LiveData<List<FavouriteRecipe>>
        get() = repository.favouriteRecipes

    fun getFavouriteRecipes() {
        viewModelScope.launch {
            repository.getFavouriteRecipes()
        }
    }
}