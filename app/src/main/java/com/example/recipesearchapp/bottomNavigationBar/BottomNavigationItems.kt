package com.example.recipesearchapp.bottomNavigationBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.recipesearchapp.R

sealed class BottomNavigationItems(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Dashboard : BottomNavigationItems(
        route = "dashboard",
        title = "Home",
        icon = Icons.Outlined.Home
    )
    object Favourite : BottomNavigationItems(
        route = "favourite",
        title = "Favourite",
        icon = Icons.Outlined.Favorite
    )
}