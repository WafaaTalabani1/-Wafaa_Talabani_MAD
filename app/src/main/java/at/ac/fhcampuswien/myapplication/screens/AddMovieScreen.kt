package at.ac.fhcampuswien.myapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.myapplication.utils.InjectorUtils
import at.ac.fhcampuswien.myapplication.R
import at.ac.fhcampuswien.myapplication.composables.RequiredFieldErrorMessage
import at.ac.fhcampuswien.myapplication.composables.notValidGenreMessage
import at.ac.fhcampuswien.myapplication.composables.notValidMessage
import at.ac.fhcampuswien.myapplication.models.Genre
import at.ac.fhcampuswien.myapplication.models.ListItemSelectable
import at.ac.fhcampuswien.myapplication.models.Movie
import at.ac.fhcampuswien.myapplication.viewModels.AddMovieViewModel
import at.ac.fhcampuswien.myapplication.widgets.SimpleAppBar
import kotlinx.coroutines.launch

@Composable
fun AddMovieScreen(navController: NavController){
    val scaffoldState = rememberScaffoldState()
    val viewModel: AddMovieViewModel = viewModel(factory = InjectorUtils.provideMovieViewModelFactory(
        LocalContext.current))
    val coroutinescope = rememberCoroutineScope()


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
            addButtonClick = { addMovie -> coroutinescope.launch {
                viewModel.addMovie(addMovie)
            }},
            InputValidationCheck = {Input ->
                viewModel.validateUserInput(Input)},
            navController,
            viewModel)
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
fun MainContent(
    modifier: Modifier = Modifier,
    addButtonClick: (addMovie: Movie) -> Unit = {},
    InputValidationCheck: (input: String) -> Boolean,
    navController: NavController,
    viewModel: AddMovieViewModel
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
            var genresError by remember { mutableStateOf(false) }


            var director by remember { mutableStateOf("") }
            var directorError by remember { mutableStateOf(false) }

            var actors by remember { mutableStateOf("") }
            var actorError by remember { mutableStateOf(false) }

            var plot by remember { mutableStateOf("") }
            var plotError by remember { mutableStateOf(false) }

            var rating by remember { mutableStateOf("") }
            var ratingError by remember { mutableStateOf(false) }

            var enabledSaveButton by remember { mutableStateOf(false) }

            val isValidTitle by remember { derivedStateOf { title.isNotBlank() && isValidTextInput(title) } }
            val isValidYear by remember { derivedStateOf { year.isNotBlank() && isValidNumberInput(year) } }
            val isValidDirector by remember { derivedStateOf { director.isNotBlank() && isValidTextInput(director)} }
            val isValidActors by remember { derivedStateOf { actors.isNotBlank() && isValidTextInput(actors)} }
            val isValidRating by remember { derivedStateOf { rating.isNotBlank() && rating.toFloatOrNull() != null } }
            val hasSelectedGenres by remember { derivedStateOf { genreItems.any { it.isSelected } } }
            val isValidPlot by remember { derivedStateOf { plot.isNotBlank()&& isValidTextInput(plot) } }
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
                onValueChange = {
                    titleError = !InputValidationCheck(it)
                    title = it
                },
                label = { Text(text = "Enter Title") },
                isError = !isValidTitle && title.isNotEmpty()
            )

            if (!isValidTitle && title.isNotEmpty()) {
                notValidMessage(msg = "Title")
            }
            RequiredFieldErrorMessage(msg = "Title", visible = titleError)





            OutlinedTextField(
                value = year,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    yearError = !InputValidationCheck(it)
                    year = it
                         },
                label = { Text(text = "Enter Year") },
                isError = !isValidYear && year.isNotEmpty() ,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            if (!isValidYear && year.isNotEmpty()) {
                notValidMessage(msg = "Year")
            }
            RequiredFieldErrorMessage(msg = "Year", visible = yearError)



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
            if (!hasSelectedGenres && genreItems.any { it.isSelected }) {
                notValidGenreMessage(msg = "Genre")
            }
            RequiredFieldErrorMessage(msg = "Genre", visible = genresError)

            OutlinedTextField(
                value = director,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    directorError = !InputValidationCheck(it)
                    director = it
                      },
                label = { Text(text = "Enter Director") },
                isError = !isValidDirector && director.isNotEmpty()
            )
            if (!isValidDirector && director.isNotEmpty()) {
                notValidMessage(msg = "Director")
            }
            RequiredFieldErrorMessage(msg = "Director", visible = directorError)


            OutlinedTextField(
                value = actors,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    actorError = !InputValidationCheck(it)
                    actors = it
                       },
                label = { Text(text = "Enter Actor") },
                isError = !isValidActors && actors.isNotEmpty()
            )
            if (!isValidActors && actors.isNotEmpty()) {
                notValidMessage(msg = "Actor")
            }
            RequiredFieldErrorMessage(msg = "Actor", visible = actorError)



            OutlinedTextField(
                value = plot,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                onValueChange = {
                    plotError = !InputValidationCheck(it)
                    plot = it
                       },
                label = { Text(textAlign = TextAlign.Start, text = "Enter Plot") },
                isError = false
            )




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
                    } },
                label = { Text(text = "Enter Rating") },
                isError = !isValidRating && rating.isNotEmpty()
            )
            if (!isValidRating && rating.isNotEmpty()) {
                notValidMessage(msg = "Rating")
            }
            RequiredFieldErrorMessage(msg = "Rating", visible = ratingError)


            val coroutinescope = rememberCoroutineScope()
            Button(
                enabled = isEnabledSaveButton,
                onClick = {
                    val selectedGenres = genreItems.filter { it.isSelected }.map { Genre.valueOf(it.title) }
                    val newMovie = Movie(
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

                coroutinescope.launch {
                    viewModel.addMovie(newMovie)}
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
