package com.example.recipesearchapp.presentation.components.bottomNavigationBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

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