package com.example.recipesearchapp.models.RandomRecipeModel

data class Ingredient(
    val id: Long,
    val name: String,
    val amount: Double,
    val unit: String,
    val nutrients: List<Nutrient2>
)
