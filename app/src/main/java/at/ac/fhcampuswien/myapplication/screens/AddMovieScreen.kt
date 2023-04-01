package at.ac.fhcampuswien.myapplication.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ac.fhcampuswien.movieapp.models.Movie
import at.ac.fhcampuswien.myapplication.R
import at.ac.fhcampuswien.myapplication.SimpleTopAppBar
import at.ac.fhcampuswien.myapplication.models.Genre
import at.ac.fhcampuswien.myapplication.models.ListItemSelectable
import at.ac.fhcampuswien.myapplication.models.MovieViewModel
import java.util.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import org.json.JSONObject.NULL


@Composable
fun AddMovieScreen(navController: NavController, viewModel: MovieViewModel){

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            SimpleTopAppBar(arrowBackClicked = { navController.popBackStack() }) {
                Text(text = stringResource(id = R.string.add_movie))
            }
        },
    ) { padding ->
        MainContent(Modifier.padding(padding),navController, viewModel)
    }
}

fun isValidTextInput(input: String): Boolean {
    return input.all { it.isLetter() || it.isWhitespace() }
}
fun isValidNumberInput(input: String): Boolean {
    return input.all { it.isDigit() }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainContent(modifier: Modifier = Modifier,navController: NavController, viewModel: MovieViewModel ) {

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(10.dp)
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {

            var title by remember {
                mutableStateOf("")
            }

            var year by remember {
                mutableStateOf("")
            }

            val genres = Genre.values().toList()

            var genreItems by remember {
                mutableStateOf(
                    genres.map { genre ->
                        ListItemSelectable(
                            title = genre.toString(),
                            isSelected = false
                        )
                    }
                )
            }

            var director by remember {
                mutableStateOf("")
            }

            var actors by remember {
                mutableStateOf("")
            }

            var plot by remember {
                mutableStateOf("")
            }

            var rating by remember {
                mutableStateOf("")
            }

            val isValidTitle by remember { derivedStateOf { title.isNotBlank() && isValidTextInput(title) } }
            val isValidYear by remember { derivedStateOf { year.isNotBlank() && isValidNumberInput(year) } }
            val isValidDirector by remember { derivedStateOf { director.isNotBlank() && isValidTextInput(title)} }
            val isValidActors by remember { derivedStateOf { actors.isNotBlank() && isValidTextInput(title)} }
            val isValidRating by remember { derivedStateOf { rating.isNotBlank() && rating.toFloatOrNull() != null } }
            val hasSelectedGenres by remember { derivedStateOf { genreItems.any { it.isSelected } } }
            val isValidPlot by remember { derivedStateOf { plot.isNotBlank()&& isValidTextInput(title) } }
            val isEnabledSaveButton by remember {
                derivedStateOf {
                    isValidTitle && isValidYear && isValidDirector &&
                            isValidActors && isValidRating && hasSelectedGenres
                }
            }

            OutlinedTextField(
                value = title,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { title = it },
                label = { Text(text = stringResource(R.string.enter_movie_title)) },
                isError = !isValidTitle && title.isNotEmpty()
            )

            if (!isValidTitle && title.isNotEmpty()) {
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = stringResource(R.string.error_invalid_title),
                    color = MaterialTheme.colors.error,
                    fontSize = 12.sp
                )
            }


            OutlinedTextField(
                value = year,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { year = it },
                label = { Text(stringResource(R.string.enter_movie_year)) },
                isError = !isValidYear && year.isNotEmpty() ,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            if (!isValidYear && year.isNotEmpty()) {
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = stringResource(R.string.error_invalid_year),
                    color = MaterialTheme.colors.error,
                    fontSize = 12.sp
                )
            }


            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(R.string.select_genres),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.h6)

            LazyHorizontalGrid(
                modifier = Modifier.height(100.dp),
                rows = GridCells.Fixed(3)){

                items(items = genreItems) { genreItem ->
                    Chip(
                        modifier = Modifier.padding(2.dp),
                        colors = ChipDefaults.chipColors(
                            backgroundColor = if (genreItem.isSelected)
                                colorResource(id = R.color.purple_200)
                            else
                                colorResource(id = R.color.white)
                        ),
                        onClick = {
                            genreItems = genreItems.map {
                                if (it.title == genreItem.title) {
                                    genreItem.copy(isSelected = !genreItem.isSelected)
                                } else {
                                    it
                                }
                            }
                        }
                    ) {
                        Text(text = genreItem.title)
                    }
                }
            }
            if (!hasSelectedGenres && genreItems.any { it.isSelected }) {
            Text(
                modifier = Modifier.padding(start = 4.dp, top = 4.dp),
                text = stringResource(R.string.error_no_genres_selected),
                color = MaterialTheme.colors.error,
                fontSize = 12.sp
            )
        }

            OutlinedTextField(
                value = director,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { director = it },
                label = { Text(stringResource(R.string.enter_director)) },
                isError = !isValidDirector && director.isNotEmpty()
            )

            if (!isValidDirector && director.isNotEmpty()) {
                Text(
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp),
                    text = stringResource(R.string.error_invalid_director),
                    color = MaterialTheme.colors.error,
                    fontSize = 12.sp
                )
            }

            OutlinedTextField(
                value = actors,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { actors = it },
                label = { Text(stringResource(R.string.enter_actors)) },
                isError = !isValidActors && actors.isNotEmpty()
            )

            if (!isValidActors && actors.isNotEmpty()) {
                Text(
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp),
                    text = stringResource(R.string.error_invalid_actors),
                    color = MaterialTheme.colors.error,
                    fontSize = 12.sp
                )
            }


            OutlinedTextField(
                value = plot,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                onValueChange = { plot = it },
                label = { Text(textAlign = TextAlign.Start, text = stringResource(R.string.enter_plot)) },
                isError = !isValidPlot && plot.isNotEmpty()
            )

            if (!isValidPlot && plot.isNotEmpty()) {
                Text(
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp),
                    text = stringResource(R.string.error_invalid_plot),
                    color = MaterialTheme.colors.error,
                    fontSize = 12.sp
                )
            }

            OutlinedTextField(
                value = rating,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    if (it.all { char -> char.isDigit() || char == '.' }) {
                        rating = it
                    }
                },
                label = { Text(stringResource(R.string.enter_rating)) },
                isError = !isValidRating && rating.isNotEmpty()
            )

            if (!isValidRating && rating.isNotEmpty()) {
                Text(
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp),
                    text = stringResource(R.string.error_invalid_rating),
                    color = MaterialTheme.colors.error,
                    fontSize = 12.sp
                )
            }

            Button(
                enabled = isEnabledSaveButton,
                onClick = {
                    val selectedGenres = genreItems.filter { it.isSelected }.map { Genre.valueOf(it.title) }
                    val newMovie = Movie(
                        id = viewModel.nextMovieId.toString(), // Generate a unique ID for the movie
                        title = title,
                        year = year,
                        genre = selectedGenres,
                        director = director,
                        actors = actors,
                        plot = plot,
                        images = listOf("","",""),
                        rating = rating.toFloat(),
                        isFavorite = false // Set the initial favorite status to false
                    )
                    viewModel.addMovie(newMovie)
                    navController.popBackStack() // Navigate back to the previous screen
                }
            ) {
                Text(text = stringResource(R.string.add))
            }

        }}
}



    fun addMovie(
        title: String,
        year: String,
        genres: List<Genre>,
        director: String,
        actors: String,
        plot: String,
        rating: Float
    ) {
        // Implement the logic to add a movie to the collection
    }

