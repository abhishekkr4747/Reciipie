package com.example.recipesearchapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipesearchapp.BuildConfig
import com.example.recipesearchapp.data.remote.ApiService
import com.example.recipesearchapp.data.remote.model.AutoCompleteRecipeModel.AutoCompleteRecipeSearchApiResponse
import com.example.recipesearchapp.data.remote.model.RandomRecipeModel.RandomRecipeApiResponse
import com.example.recipesearchapp.data.remote.model.SearchRecipeModel.SearchRecipeApiResponse
import com.example.recipesearchapp.data.room.database.FavouriteRecipeDatabase
import com.example.recipesearchapp.data.room.model.FavouriteRecipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val apiService: ApiService,
    private val favouriteRecipeDatabase: FavouriteRecipeDatabase
) {

    private val _randomRecipes = MutableLiveData<RandomRecipeApiResponse>()
    val randomRecipes: LiveData<RandomRecipeApiResponse>
        get() = _randomRecipes

    private val _randomRecipesLoading = MutableLiveData(true)
    val randomRecipesLoading: LiveData<Boolean>
        get() = _randomRecipesLoading

    suspend fun getRandomRecipes() {
        val result = apiService.getRandomRecipe(
            apiKey = BuildConfig.API_KEY_1,
            number = "10",
            include = "vegetarian",
            includeNutrition = true
        )

        if (result.isSuccessful) {
            _randomRecipes.postValue(result.body())
            _randomRecipesLoading.postValue(false)
        } else {
            Log.d("TAG", "Error in getRandomRecipes: ${result.code()}")
        }
    }

    suspend fun getSingleRandomRecipe(): RandomRecipeApiResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getRandomRecipe(
                    apiKey = BuildConfig.API_KEY_3,
                    number = "1",
                    "indian",
                    true
                )

                if (response.isSuccessful) {
                    response.body()
                } else {
                    Log.d("TAG", "ERROR: ${response.code()}")
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    private val _allRecipes = MutableLiveData<RandomRecipeApiResponse>()
    val allRecipes: LiveData<RandomRecipeApiResponse>
        get() = _allRecipes

    private val _allRecipesLoading = MutableLiveData(true)
    val allRecipesLoading: LiveData<Boolean>
        get() = _allRecipesLoading

    suspend fun getAllRecipe() {
        val result = apiService.getRandomRecipe(
            apiKey = BuildConfig.API_KEY_2,
            number = "10",
            include = "indian",
            includeNutrition = true
        )

        if (result.isSuccessful) {
            _allRecipes.postValue(result.body())
            _allRecipesLoading.postValue(false)
        } else {
            Log.d("TAG", "Error in getAllRecipe: ${result.code()}")
        }
    }

    private val _autoCompleteRecipes = MutableLiveData<List<AutoCompleteRecipeSearchApiResponse>>()
    val autoCompleteRecipes: LiveData<List<AutoCompleteRecipeSearchApiResponse>>
        get() = _autoCompleteRecipes

    suspend fun getAutoCompleteRecipe(query: String) {
        val result = apiService.getAutocompleteRecipeSearch(
            apiKey = BuildConfig.API_KEY_3,
            number = "5",
            query
        )

        if (result.isSuccessful && result.body() != null) {
            _autoCompleteRecipes.postValue(result.body())
        } else {
            Log.d("TAG", "Error in getAutoCompleteRecipe: ${result.code()}")
        }
    }

    private val _searchRecipes = MutableLiveData<List<SearchRecipeApiResponse>>()
    val searchRecipes: LiveData<List<SearchRecipeApiResponse>>
        get() = _searchRecipes

    suspend fun getSearchedRecipes(ids: String) {
        val result = apiService.getRecipeInformationBulk(
            apiKey = BuildConfig.API_KEY_4,
            ids = ids,
            includeNutrition = true
        )

        if (result.isSuccessful && result.body() != null) {
            _searchRecipes.postValue(result.body())
        } else {
            Log.d("TAG", "Error in getSearchedRecipes:: ${result.code()}")
        }
    }


    suspend fun addFavouriteRecipe(favRecipe: FavouriteRecipe) {
        try {
            favouriteRecipeDatabase.dao().upsertRecipe(favRecipe)

        } catch (e: Exception) {
            Log.d("Error", "Error in addFavouriteRecipe: $e")
        }
    }

    suspend fun deleteFavouriteRecipe(favRecipe: FavouriteRecipe) {
        try {
            favouriteRecipeDatabase.dao().deleteRecipe(favRecipe)

        } catch (e: Exception) {
            Log.d("Error", "Error in deleteFavouriteRecipe: $e")
        }
    }

    private val _favouriteRecipes = MutableLiveData<List<FavouriteRecipe>>()
    val favouriteRecipes: LiveData<List<FavouriteRecipe>>
        get() = _favouriteRecipes

    suspend fun getFavouriteRecipes() {
        try {
            _favouriteRecipes.postValue(favouriteRecipeDatabase.dao().getAllFavouriteRecipes())
        } catch (e: Exception) {
            Log.d("Error", "Error in getFavouriteRecipes: $e")
        }
    }
}