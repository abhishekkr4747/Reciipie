package com.example.recipesearchapp.di

import android.content.Context
import com.example.recipesearchapp.ReciipieApplication
import com.example.recipesearchapp.presentation.MainActivity
import com.example.recipesearchapp.service.WorkerClass
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DaggerModule::class])
interface ApplicationDaggerComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(workerClass: WorkerClass)

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): ApplicationDaggerComponent
    }
}