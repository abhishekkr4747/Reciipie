package com.example.recipesearchapp.service

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.recipesearchapp.R
import com.example.recipesearchapp.ReciipieApplication
import com.example.recipesearchapp.data.remote.model.RandomRecipeModel.Recipe
import com.example.recipesearchapp.presentation.MainActivity
import com.example.recipesearchapp.viewmodel.RecipeViewModel
import com.example.recipesearchapp.viewmodel.RecipeViewModelFactory
import com.example.recipesearchapp.viewmodel.SharedViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class WorkerClass(context: Context, params: WorkerParameters): Worker(context, params) {

    @Inject
    lateinit var recipeViewModelFactory: RecipeViewModelFactory

    private lateinit var  recipeViewModel: RecipeViewModel
    private val sharedViewModel = SharedViewModel()

    override fun doWork(): Result {
        (applicationContext as ReciipieApplication).daggerComponent.inject(this)

        recipeViewModel = recipeViewModelFactory.create(RecipeViewModel::class.java)

        return try {
            runBlocking {
                val response = recipeViewModel.getSingleRandomRecipe()
                response?.let {
                    val recipe = it.recipes.firstOrNull()
                    recipe?.let {
                        Log.d("WORK DONE", "doWork: recipe: $recipe")
                        sharedViewModel.addRecipe(newRecipe = recipe)
                        showNotification(recipe)

                    } ?: run {
                        Log.e("WorkerClass", "doWork: No recipe found in the response")
                    }
                } ?: run {
                    Log.e("WorkerClass", "doWork: API response is null")
                }
            }
            Result.success()
        } catch (e: Exception) {
            Log.e("WorkerClass", "doWorkException: ${e.localizedMessage}", e)
            Result.failure()
        }
    }

    private fun showNotification(recipe: Recipe) {
        val channelId = "recipe_notification_channel"
        createNotificationChannel(channelId)

        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("navigate_to_recipe_view", true)
        }

        val pendingIntent: PendingIntent = PendingIntent
            .getActivity(
                applicationContext,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Check out this recipe!")
            .setContentText(recipe.title)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(applicationContext)) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ActivityCompat.requestPermissions(
                        applicationContext as Activity,
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        0
                    )
                }
                return
            }
            notify(1, notification.build())
        }
    }

    private fun createNotificationChannel(channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Recipe Notification",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for new recipes"
            }
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}