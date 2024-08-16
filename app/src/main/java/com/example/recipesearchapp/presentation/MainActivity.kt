package com.example.recipesearchapp.presentation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.recipesearchapp.ReciipieApplication
import com.example.recipesearchapp.presentation.components.bottomNavigationBar.BottomBar
import com.example.recipesearchapp.presentation.navigation.Screen
import com.example.recipesearchapp.presentation.navigation.SetupNavGraph
import com.example.recipesearchapp.presentation.theme.RecipeSearchAppTheme
import com.example.recipesearchapp.service.WorkerClass
import com.example.recipesearchapp.viewmodel.MainViewModel
import com.example.recipesearchapp.viewmodel.RecipeViewModel
import com.example.recipesearchapp.viewmodel.RecipeViewModelFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainActivity : ComponentActivity() {
    lateinit var recipeViewModel: RecipeViewModel

    @Inject
    lateinit var recipeViewModelFactory: RecipeViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT,
            ),

            navigationBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
            android.graphics.Color.TRANSPARENT,
        )
        )
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }

        (application as ReciipieApplication).daggerComponent.inject(this)

        recipeViewModel = ViewModelProvider(this, recipeViewModelFactory).get(RecipeViewModel::class.java)

        setContent {
            val navController: NavHostController = rememberNavController()
            MyApp(navController,recipeViewModel)
        }

        schedulePeriodicWork(this)

    }

}

@Composable
fun MyApp(navController: NavHostController, recipeViewModel: RecipeViewModel) {
    RecipeSearchAppTheme {

        var buttonsVisible by remember { mutableStateOf(false) }

        Scaffold(
            bottomBar = {
                if (buttonsVisible) {
                    BottomBar(
                        navController = navController,
                        state = buttonsVisible,
                        modifier = Modifier
                    )
                }
            },
            contentWindowInsets = if (buttonsVisible) WindowInsets.systemBars else WindowInsets(0,0,0,0)
            ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = paddingValues.calculateBottomPadding())
                    .padding(top = paddingValues.calculateTopPadding())
                    .background(color = Color.White)
            ) {
                SetupNavGraph(navController = navController, recipeViewModel) {
                        isVisible ->
                    buttonsVisible = isVisible
                }
            }
        }
    }
}

fun schedulePeriodicWork(context: Context) {
    val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
    val periodicWorkRequest = PeriodicWorkRequestBuilder<WorkerClass>(24, TimeUnit.HOURS)
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "RandomRecipeWork",
        ExistingPeriodicWorkPolicy.KEEP,
        periodicWorkRequest
    )
}







