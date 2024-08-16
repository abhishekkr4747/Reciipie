package com.example.recipesearchapp

import android.app.Application
import com.example.recipesearchapp.di.ApplicationDaggerComponent
import com.example.recipesearchapp.di.DaggerApplicationDaggerComponent

class ReciipieApplication: Application() {

    lateinit var daggerComponent: ApplicationDaggerComponent

    override fun onCreate() {
        super.onCreate()
        daggerComponent = DaggerApplicationDaggerComponent.factory().create(this)
    }
}