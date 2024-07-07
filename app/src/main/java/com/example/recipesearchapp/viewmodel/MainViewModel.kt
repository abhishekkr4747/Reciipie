package com.example.recipesearchapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesearchapp.BuildConfig
import com.example.recipesearchapp.data.remote.ApiService
import com.example.recipesearchapp.data.remote.model.AutoCompleteRecipeModel.AutoCompleteRecipeSearchApiResponse
import com.example.recipesearchapp.data.remote.model.RandomRecipeModel.RandomRecipeApiResponse
import com.example.recipesearchapp.data.remote.model.SearchRecipeModel.SearchRecipeApiResponse
import com.example.recipesearchapp.data.room.dao.FavouriteRecipeDao
import com.example.recipesearchapp.data.room.model.FavouriteRecipe
import com.example.recipesearchapp.presentation.google_sign_in.SignInResult
import com.example.recipesearchapp.presentation.google_sign_in.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel: ViewModel() {
    var randomRecipesState = mutableStateOf<RandomRecipeApiResponse?>(null)
    var allRecipeState = mutableStateOf<RandomRecipeApiResponse?>(null)
    var autoCompleteRecipeState = mutableStateListOf<AutoCompleteRecipeSearchApiResponse>()
    var searchRecipeState = mutableStateListOf<SearchRecipeApiResponse>()
    var isPopularRecipeLoading = mutableStateOf(true)
    var isAllRecipeLoading = mutableStateOf(true)

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
                    apiKey = BuildConfig.API_KEY_1,
                    number = "10",
                    "vegetarian",
                    true
                )

                if(response.isSuccessful) {
                    randomRecipesState.value = response.body()
                    isPopularRecipeLoading.value = false
                } else {
                    Log.d("error", "onResponse: ${response.code()}")
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

     fun getAllRecipe() {
        viewModelScope.launch {
            try {
                val response = apiService.getRandomRecipe(
                    apiKey = BuildConfig.API_KEY_2,
                    number = "10",
                    "indian",
                    true
                )

                if(response.isSuccessful) {
                    allRecipeState.value = response.body()
                    isAllRecipeLoading.value = false
                } else {
                    Log.d("error", "getAllRecipe: ${response.code()}")
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getAutoCompleteRecipe(query: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getAutocompleteRecipeSearch(
                    apiKey = BuildConfig.API_KEY_3,
                    number = "5",
                    query
                )

                if(response.isSuccessful) {
                    response.body()?.let {
                        autoCompleteRecipeState.clear()
                        autoCompleteRecipeState.addAll(it)
                    }
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
                    apiKey = BuildConfig.API_KEY_4,
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

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        ) }
    }

    fun resetState() {
        _state.update { SignInState() }
    }
}