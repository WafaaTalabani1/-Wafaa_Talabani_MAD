package at.ac.fhcampuswien.myapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.myapplication.viewModels.MovieViewModel
import at.ac.fhcampuswien.myapplication.R
import at.ac.fhcampuswien.myapplication.composables.ErrorMessage
import at.ac.fhcampuswien.myapplication.models.Genre
import at.ac.fhcampuswien.myapplication.models.ListItemSelectable
import at.ac.fhcampuswien.myapplication.models.Movie
import at.ac.fhcampuswien.myapplication.widgets.SimpleAppBar

@Composable
fun AddMovieScreen(navController: NavController, viewModel: MovieViewModel){
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            SimpleAppBar(
                title = stringResource(id = R.string.add_movie),
                navController = navController)
        },
    ) { padding ->
        MainContent(
            Modifier.padding(padding),
            addButtonClick = { addMovie -> viewModel.addMovie(addMovie)},
            InputValidationCheck = {Input -> viewModel.validateUserInput(Input)},
            navController,
            viewModel)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    addButtonClick: (addMovie: Movie) -> Unit = {},
    InputValidationCheck: (input: String) -> Boolean,
    navController: NavController,
    viewModel: MovieViewModel
) {
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

            var title by remember { mutableStateOf("") }
            var titleError by remember { mutableStateOf(false) }

            var year by remember { mutableStateOf("") }
            var yearError by remember { mutableStateOf(false) }

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

            var director by remember { mutableStateOf("") }
            var directorError by remember { mutableStateOf(false) }

            var actors by remember { mutableStateOf("") }
            var actorError by remember { mutableStateOf(false) }

            var plot by remember { mutableStateOf("") }
            var plotError by remember { mutableStateOf(false) }

            var rating by remember { mutableStateOf("") }
            var ratingError by remember { mutableStateOf(false) }

            var enabledSaveButton by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = title,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    titleError = !InputValidationCheck(it)
                    title = it
                    enabledSaveButton =  enableButton(titleError, yearError, directorError, actorError, plotError, ratingError)
                },
                label = { Text(text = "Enter Title") },
                isError = titleError
            )
            ErrorMessage(msg = "Title", visible = titleError)


            OutlinedTextField(
                value = year,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    yearError = !InputValidationCheck(it)
                    year = it
                    enabledSaveButton =  enableButton(titleError, yearError, directorError, actorError, plotError, ratingError)
                },
                label = { Text(text = "Enter Year") },
                isError = yearError
            )
            ErrorMessage(msg = "Year", visible = yearError)

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(R.string.select_genres),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.h6)

            LazyHorizontalGrid(
                modifier = Modifier.height(100.dp),
                rows = GridCells.Fixed(3)){
                items(genreItems) { genreItem ->
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

            OutlinedTextField(
                value = director,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    directorError = !InputValidationCheck(it)
                    director = it
                    enabledSaveButton =  enableButton(titleError, yearError, directorError, actorError, plotError, ratingError)
                },
                label = { Text(text = "Enter Director") },
                isError = directorError
            )
            ErrorMessage(msg = "Director", visible = directorError)

            OutlinedTextField(
                value = actors,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    actorError = !InputValidationCheck(it)
                    actors = it
                    enabledSaveButton =  enableButton(!titleError, !yearError, !directorError, !actorError, !plotError, !ratingError)
                },
                label = { Text(text = "Enter Actor") },
                isError = actorError
            )
            ErrorMessage(msg = "Actor", visible = actorError)

            OutlinedTextField(
                value = plot,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                onValueChange = {
                    plotError = !InputValidationCheck(it)
                    plot = it
                    enabledSaveButton =  enableButton(titleError, yearError, directorError, actorError, plotError, ratingError)
                },
                label = { Text(textAlign = TextAlign.Start, text = "Enter Plot") },
                isError = plotError
            )
            ErrorMessage(msg = "Plot", visible = plotError)

            OutlinedTextField(
                value = rating,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    ratingError = !InputValidationCheck(it)
                    rating = if(it.startsWith("0")) {
                        ""
                    } else {
                        it
                    }
                    enabledSaveButton =  enableButton(titleError, yearError, directorError, actorError, plotError, ratingError)
                },
                label = { Text(text = "Enter Rating") },
                isError = ratingError
            )
            ErrorMessage(msg = "Rating", visible = ratingError)


            Button(
                enabled = enabledSaveButton,
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
                        favoriteAsDefault = false // Set the initial favorite status to false
                    )
                    viewModel.addMovie(newMovie)
                    navController.popBackStack() // Navigate back to the previous screen

                }
            ) {
                Text(text = stringResource(R.string.add))
            }
        }
    }
}


fun enableButton(titleValid: Boolean, yearValid: Boolean, directorValid: Boolean, actorValid: Boolean, plotValid: Boolean, ratingValid: Boolean): Boolean {
    return titleValid && yearValid && directorValid && actorValid && plotValid && ratingValid
}
