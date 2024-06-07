package com.example.recipesearchapp.api

import com.example.recipesearchapp.models.RandomRecipeApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("recipes/random")
    fun getRandomRecipe(
        @Query("apiKey") apiKey: String,
        @Query("number") number: String
    ): Call<RandomRecipeApiResponse>
}