package com.example.recipesearchapp.data.remote.model.SearchRecipeModel

data class ProductMatch(
    val id: Long,
    val title: String,
    val description: String,
    val price: String,
    val imageUrl: String,
    val averageRating: Double,
    val ratingCount: Double,
    val score: Double,
    val link: String
)

