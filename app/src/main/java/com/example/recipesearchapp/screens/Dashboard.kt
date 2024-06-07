package com.example.recipesearchapp.screens



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipesearchapp.widgets.AllRecipeColumn
import com.example.recipesearchapp.widgets.BottomNavigationBar
import com.example.recipesearchapp.widgets.RecipeRow
import com.example.recipesearchapp.widgets.SectionTitle

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun Dashboard() {
    val searchQuery by remember { mutableStateOf("") }
    Scaffold(
        bottomBar = {
            BottomNavigationBar()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(it)
        ) {
            GreetingSection(userName = "User")
            Spacer(modifier = Modifier.height(10.dp))
            SearchBar()
            Spacer(modifier = Modifier.height(25.dp))
            SectionTitle(title = "Popular Recipes")
            Spacer(modifier = Modifier.height(10.dp))
            RecipeRow()
            Spacer(modifier = Modifier.height(20.dp))
            SectionTitle(title = "All recipes")
            Spacer(modifier = Modifier.height(10.dp))
            AllRecipeColumn()
        }
    }

}

@Composable
fun GreetingSection(userName: String = "User") {
    Column {
        Text(
            text = "👋 Hey $userName",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Discover tasty and healthy recipes",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}





@Composable
fun SearchBar() {
    val searchQuery = remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray.copy(alpha = 0.2f))
            .padding(8.dp)
    ) {
        BasicTextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Text
            ),
            decorationBox = { innerTextField ->
                if (searchQuery.value.isEmpty()) {
                    Text(text = "Search any recipe", color = Color.Gray)
                }
                innerTextField()
            }
        )
    }
}



