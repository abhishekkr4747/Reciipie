package com.example.recipesearchapp.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipesearchapp.R
import com.example.recipesearchapp.widgets.CircularItemRow
import com.example.recipesearchapp.widgets.ExpandableSection
import com.example.recipesearchapp.widgets.RecipeInfoCard
import com.example.recipesearchapp.widgets.SectionTitle

@Preview(showBackground = true)
@Composable
fun RecipeView() {
    var filled by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.paneer),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
            )
            IconButton(
                onClick = { filled = !filled },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(Color.White, shape = CircleShape)
            ) {
                Icon(painter = if (filled) painterResource(id = R.drawable.filled_favourite) else painterResource(id = R.drawable.favourite),
                    contentDescription = null,
                    tint = Color.Red)
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceEvenly
            ) {
                RecipeInfoCard( "Ready in", "25 min")
                RecipeInfoCard("Servings", "6")
                RecipeInfoCard("Price/serving", "156")
            }

            Spacer(modifier = Modifier.height(16.dp))

            SectionTitle(title = "Ingredients")

            Spacer(modifier = Modifier.height(10.dp))

            CircularItemRow()

            Spacer(modifier = Modifier.height(16.dp))

            SectionTitle(title = "Instructions")

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Lorem ipsum dolor sit amet consectetur. Sagittis facilisis aliquet aenean lorem ullamcorper et. Risus lectus id sed fermentum in. At porta sed ut lorem volutpat elementum mi sollicitudin. Laoreet tempor nullam velit dui amet mauris sed ac sem.",
                color = Color.Gray,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            SectionTitle(title = "Equipments")

            Spacer(modifier = Modifier.height(10.dp))

            CircularItemRow()

            Spacer(modifier = Modifier.height(16.dp))

            SectionTitle(title = "Quick Summary")

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Lorem ipsum dolor sit amet consectetur. Sagittis facilisis aliquet aenean lorem ullamcorper et. Risus lectus id sed fermentum in. At porta sed ut lorem volutpat elementum mi sollicitudin. Laoreet tempor nullam velit dui amet mauris sed ac sem.",
                color = Color.Gray,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            ExpandableSection(title = "Nutrition", expand = true)

            Spacer(modifier = Modifier.height(16.dp))

            ExpandableSection(title = "Bad for health nutrition", expand = false)

            Spacer(modifier = Modifier.height(16.dp))

            ExpandableSection(title = "Good for health nutrition", expand = false)
        }
    }
}



