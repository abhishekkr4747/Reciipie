package com.example.recipesearchapp.data.remote.model.RandomRecipeModel

data class Ingredient(
    val id: Long,
    val name: String,
    val amount: Double,
    val unit: String,
    val nutrients: List<com.example.recipesearchapp.data.remote.model.RandomRecipeModel.Nutrient2>
)
