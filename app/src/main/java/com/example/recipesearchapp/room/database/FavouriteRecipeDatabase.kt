package com.example.recipesearchapp.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipesearchapp.room.converter.Converters
import com.example.recipesearchapp.room.model.FavouriteRecipe
import com.example.recipesearchapp.room.dao.FavouriteRecipeDao

@Database(
    entities = [FavouriteRecipe::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class FavouriteRecipeDatabase: RoomDatabase() {
    abstract fun dao(): FavouriteRecipeDao
}