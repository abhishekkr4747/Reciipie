package com.example.recipesearchapp.data.remote

import com.example.recipesearchapp.data.remote.model.AutoCompleteRecipeModel.AutoCompleteRecipeSearchApiResponse
import com.example.recipesearchapp.data.remote.model.RandomRecipeModel.RandomRecipeApiResponse
import com.example.recipesearchapp.data.remote.model.SearchRecipeModel.SearchRecipeApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("recipes/random")
     suspend fun getRandomRecipe(
        @Query("apiKey") apiKey: String,
        @Query("number") number: String,
        @Query("include-tags") include: String,
        @Query("includeNutrition") includeNutrition: Boolean
    ): Response<RandomRecipeApiResponse>

     @GET("recipes/autocomplete")
     suspend fun getAutocompleteRecipeSearch(
         @Query("apiKey") apiKey: String,
         @Query("number") number: String,
         @Query("query") query: String
     ): Response<List<AutoCompleteRecipeSearchApiResponse>>

     @GET("recipes/informationBulk")
     suspend fun getRecipeInformationBulk(
         @Query("apiKey") apiKey: String,
         @Query("ids") ids: String,
         @Query("includeNutrition") includeNutrition: Boolean
     ): Response<List<SearchRecipeApiResponse>>
}