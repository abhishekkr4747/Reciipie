package com.example.recipesearchapp.data.remote.model.SearchRecipeModel

data class WinePairing(
    val pairedWines: List<String>,
    val pairingText: String,
    val productMatches: List<ProductMatch>
)
