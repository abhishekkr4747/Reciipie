package com.example.recipesearchapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.recipesearchapp.R
import com.example.recipesearchapp.data.remote.model.RandomRecipeModel.Equipment
import com.example.recipesearchapp.data.remote.model.RandomRecipeModel.Ingredient2


@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
}


@Composable
fun AllRecipeCard(
    title: String,
    cookingTime: String,
    imageUrl: String?,
    onItemClicked: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(12.dp))
            .clickable { onItemClicked() },
        shadowElevation = 8.dp,
        border = BorderStroke(width = 1.dp,
            color = Color(0xFFE7F0F8))
    ) {
        Row{

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.padding(4.dp))

            Column {
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = title,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.padding(4.dp))

                Text(
                    text = "Ready in $cookingTime minutes",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color(0xFF606F89),
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun RecipeCard(
    title: String,
    cookingTime: String,
    imageUrl: String?,
    onItemClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(156.dp)
            .clip(shape = RoundedCornerShape(14.dp))
            .background(Color.Black)
            .clickable { onItemClicked() }
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Recipe Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.5f)
        )
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.White,
                maxLines = 1
            )
            Text(
                text = "Ready in $cookingTime min",
                fontSize = 12.sp,
                color = Color(0xFFE7F0F8),
                maxLines = 1
            )
        }
    }
}


@Preview
@Composable
fun RecipeInfoCard(
    title: String = "Ready in",
    info: String = "25 min",
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .height(56.dp)
            .width(104.dp)
            .border(
                width = 1.dp,
                color = Color(0xFFE7F0F8),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Text(
            text = title,
            color = Color(0xFF606F89),
            fontSize = 12.sp
        )
        Text(
            text = info,
            color = Color(0xFFE54900),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CircularItemElement(
    name: String,
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(86.dp)
                .clip(CircleShape)
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = name,
            modifier = Modifier.paddingFromBaseline(top = 20.dp),
            fontSize = 12.sp,
        )
    }
}

@Composable
fun CircularItemRowIngredients(
    ingredients: List<Ingredient2>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(ingredients) {ingredient->
            CircularItemElement(name = ingredient.name, imageUrl = ingredient.image)
        }
    }
}

@Composable
fun CircularItemRowEquipments(
    equipments: List<Equipment>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(equipments) {equipment->
            CircularItemElement(name = equipment.name, imageUrl = equipment.image)
        }
    }
}

@Composable
fun ExpandableSection(
    title: String = "abc",
    text: String = "Lorem ipsum dolor sit amet consectetur. Sagittis facilisis aliquet aenean lorem ullamcorper et. Risus lectus id sed fermentum in. At porta sed ut lorem volutpat elementum mi sollicitudin. Laoreet tempor nullam velit dui amet mauris sed ac sem",
    expand: Boolean = true
) {
    var expanded by remember { mutableStateOf(expand) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFF2F7FD))
            .padding(top = 8.dp, bottom = 8.dp)
            .clickable { expanded = !expanded }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SectionTitle(title = title)


            Icon(
                imageVector = if (expanded) ImageVector.vectorResource(R.drawable.arrow_drop_up_circle) else ImageVector.vectorResource(R.drawable.arrow_drop_down_circle),
                contentDescription = null,
                modifier = Modifier.clickable { expanded = !expanded }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        if(expanded) {

            Text(
                text = text,
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun SearchRecipeElement(
    title: String,
    onItemClicked: () -> Unit
    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(8.dp)
            .clickable { onItemClicked() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.search_recipe_icon),
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(text = title)
    }
}

