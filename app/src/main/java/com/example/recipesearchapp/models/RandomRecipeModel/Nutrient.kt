package com.example.recipesearchapp.models.RandomRecipeModel

data class Nutrient(
    val name: String,
    val amount: Double,
    val unit: String,
    val percentOfDailyNeeds: Double
)
