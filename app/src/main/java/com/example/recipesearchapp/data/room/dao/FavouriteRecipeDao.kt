package com.example.recipesearchapp.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.recipesearchapp.data.room.model.FavouriteRecipe

@Dao
interface FavouriteRecipeDao {

    @Upsert
    suspend fun upsertRecipe(recipe: FavouriteRecipe)

    @Delete
    suspend fun deleteRecipe(recipe: FavouriteRecipe)

    @Query("SELECT * FROM `favouriterecipe-table`")
    suspend fun getAllFavouriteRecipes(): List<FavouriteRecipe>
}