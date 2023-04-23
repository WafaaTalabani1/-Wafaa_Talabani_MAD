package at.ac.fhcampuswien.myapplication.widgets

import androidx.compose.runtime.Composable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import at.ac.fhcampuswien.myapplication.R
import at.ac.fhcampuswien.myapplication.models.Movie


@Composable
fun MovieImage(data: String?, description: String){
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.noimageavailable),
        error = painterResource(R.drawable.noimageavailable), /*if img request is unsuccessful*/
        fallback = painterResource(R.drawable.noimageavailable), /*if data is null*/
        contentDescription =  description,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun ImageRow(images: List<String>, title: String){
    Spacer(Modifier.size(10.dp))
    Divider(thickness = 1.5.dp, color = Color.LightGray)
    Text(text = title,
        style = MaterialTheme.typography.h6,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    )
    LazyRow(Modifier.fillMaxWidth()){
        items(images) {
            Card(
                Modifier
                    .width(400.dp)
                    .height(250.dp)
                    .padding(5.dp),
                shape = RoundedCornerShape(3.dp)
            ) {
                MovieImage(data = it, description = "")
            }
        }
    }
}

@Composable
fun MovieRow(movie: Movie, onFavoriteClick: (movie: Movie) -> Unit = {}, onItemClick: (Long) -> Unit = {}){
    val padding = 10.dp
    // State Holder
    var clickArrowIcon by remember { mutableStateOf(false) }

    Card(
        Modifier
            .fillMaxWidth()
            .padding(padding)
            .clickable { onItemClick(movie.id) },
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                MovieImage(data = movie.images?.get(0), description = "")
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.TopEnd
                ) {
                    var favoriteIcon = Icons.Default.FavoriteBorder
                    if (movie.isFavorite){ favoriteIcon = Icons.Default.Favorite }
                    Icon(
                        modifier = Modifier
                            .clickable { onFavoriteClick(movie) }
                            .size(35.dp),
                        contentDescription = "Add to Favorites",
                        tint = MaterialTheme.colors.secondary,
                        imageVector = favoriteIcon
                    )
                }
            }
            Spacer(Modifier.size(padding))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(padding),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = movie.title, style = MaterialTheme.typography.h6)
                var arrowIcon = Icons.Default.KeyboardArrowUp
                if (clickArrowIcon){ arrowIcon = Icons.Default.KeyboardArrowDown }
                Icon(
                    modifier = Modifier
                        .clickable {
                            clickArrowIcon = !clickArrowIcon

                        }
                        .size(35.dp),
                    contentDescription = "Show Details",
                    imageVector = arrowIcon
                )
            }
            AnimatedVisibility(visible = clickArrowIcon){
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(padding)) {
                    Text(style = MaterialTheme.typography.body1,
                        text = "Director: ${movie.director} \n" +
                                "Released: ${movie.year} \n" +
                                "Genre: ${movie.genre} \n" +
                                "Actors: ${movie.actors} \n" +
                                "Rating: ${movie.rating}")
                    Spacer(Modifier.size(padding))
                    Divider(thickness = 1.5.dp, color = Color.LightGray)
                    Spacer(Modifier.size(padding))
                    Text(text = "Plot: ${movie.plot}", Modifier.padding(padding),
                        style = MaterialTheme.typography.body2)
                }
            }
        }
    }
}