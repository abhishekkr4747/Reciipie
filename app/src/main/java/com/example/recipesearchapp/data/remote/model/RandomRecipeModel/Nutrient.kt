package com.example.recipesearchapp.data.remote.model.RandomRecipeModel

data class Nutrient(
    val name: String,
    val amount: Double,
    val unit: String,
    val percentOfDailyNeeds: Double
)
