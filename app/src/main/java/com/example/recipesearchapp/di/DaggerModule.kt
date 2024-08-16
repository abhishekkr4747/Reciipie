package com.example.recipesearchapp.di

import android.content.Context
import androidx.room.Room
import com.example.recipesearchapp.data.remote.ApiService
import com.example.recipesearchapp.data.room.database.FavouriteRecipeDatabase
import com.example.recipesearchapp.utils.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DaggerModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideFavRecipeDB(context: Context): FavouriteRecipeDatabase {
        return Room.databaseBuilder(
            context,
            FavouriteRecipeDatabase::class.java,
            "favouriterecipe-database"
        ).build()
    }
}