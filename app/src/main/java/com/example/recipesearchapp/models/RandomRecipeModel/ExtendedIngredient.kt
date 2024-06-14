package com.example.recipesearchapp.models.RandomRecipeModel

data class ExtendedIngredient(
    val id: Long,
    val aisle: String,
    val image: String?,
    val consistency: String,
    val name: String,
    val nameClean: String,
    val original: String,
    val originalName: String,
    val amount: Double,
    val unit: String,
    val meta: List<String>,
    val measures: Measures,
)
