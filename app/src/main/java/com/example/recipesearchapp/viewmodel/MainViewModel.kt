package com.example.recipesearchapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesearchapp.api.ApiService
import com.example.recipesearchapp.models.AutoCompleteRecipeModel.AutoCompleteRecipeSearchApiResponse
import com.example.recipesearchapp.models.RandomRecipeModel.RandomRecipeApiResponse
import com.example.recipesearchapp.models.SearchRecipeModel.SearchRecipeApiResponse
import com.example.recipesearchapp.room.model.FavouriteRecipe
import com.example.recipesearchapp.room.dao.FavouriteRecipeDao
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel: ViewModel() {
    var randomRecipesState = mutableStateOf<RandomRecipeApiResponse?>(null)
    var allRecipeState = mutableStateOf<RandomRecipeApiResponse?>(null)
    var autoCompleteRecipeState = mutableStateListOf<AutoCompleteRecipeSearchApiResponse>()
    var searchRecipeState = mutableStateListOf<SearchRecipeApiResponse>()

    val BASE_URL = "https://api.spoonacular.com/"
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)

     fun getRandomRecipe() {
        viewModelScope.launch {
            try {
                val response = apiService.getRandomRecipe(
                    apiKey = "a2dfbbe0e8fe493286c8002c56ec6d13",
                    number = "10",
                    "vegetarian",
                    true
                )

                if(response.isSuccessful) {
                    randomRecipesState.value = response.body()
                    Log.d("TAG", "onResponse: ${randomRecipesState.value!!.recipes}")
                } else {
                    Log.d("error", "onResponse: ${response.code()}")
                }

            } catch (e: Exception) {
                Log.d("error", "onResponse: ${e.message}")
            }
        }
    }

     fun getAllRecipe() {
        viewModelScope.launch {
            try {
                val response = apiService.getRandomRecipe(
                    apiKey = "a2dfbbe0e8fe493286c8002c56ec6d13",
                    number = "10",
                    "indian",
                    true
                )

                if(response.isSuccessful) {
                    allRecipeState.value = response.body()
                    Log.d("TAG", "getAllRecipe: ${allRecipeState.value!!.recipes}")
                } else {
                    Log.d("error", "getAllRecipe: ${response.code()}")
                }

            } catch (e: Exception) {
                Log.d("error", "getAllRecipe: ${e.message}")
            }
        }
    }

    fun getAutoCompleteRecipe(query: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getAutocompleteRecipeSearch(
                    apiKey = "6eac2dde0585480b9ac97353999eaf3c",
                    number = "5",
                    query
                )

                if(response.isSuccessful) {
                    response.body()?.let {
                        autoCompleteRecipeState.clear()
                        autoCompleteRecipeState.addAll(it)
                    }
                    Log.d("TAG", "getAutoCompleteRecipe: ${autoCompleteRecipeState}")
                } else {
                    Log.d("error", "getAutoCompleteRecipe: ${response.code()}")
                }

            } catch (e: Exception) {
                Log.d("error", "getAutoCompleteRecipe: ${e.message}")
            }
        }
    }

    fun getSearchRecipe(ids: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getRecipeInformationBulk(
                    apiKey = "6eac2dde0585480b9ac97353999eaf3c",
                    ids = ids,
                    includeNutrition = true
                )

                if(response.isSuccessful) {
                    response.body()?.let {
                        searchRecipeState.clear()
                        searchRecipeState.addAll(it)
                    }
                    Log.d("success", "getAutoCompleteRecipe: ${searchRecipeState}")
                } else {
                    Log.d("error", "getAutoCompleteRecipe: ${response.code()}")
                }

            } catch (e: Exception) {
                Log.d("error", "getAutoCompleteRecipe: ${e.message}")
            }
        }
    }

    fun upsertFavouriteRecipe(recipeDao: FavouriteRecipeDao, recipe: FavouriteRecipe) {
        viewModelScope.launch {
            try {
                recipeDao.upsertRecipe(recipe)
            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }

    fun deleteFavouriteRecipe(recipeDao: FavouriteRecipeDao, recipe: FavouriteRecipe) {
        viewModelScope.launch {
            try {
                recipeDao.deleteRecipe(recipe)
            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }
}