package at.ac.fhcampuswien.myapplication.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.movieapp.models.Movie
import at.ac.fhcampuswien.movieapp.models.getMovies

class MovieViewModel : ViewModel() {
    private val movieList = getMovies().toMutableList()
    private val favoriteMovies = mutableSetOf<String>()

    val movies: List<Movie>
    get() = movieList

    val favoriteMoviesList: List<Movie>
    get() = movieList.filter { favoriteMovies.contains(it.id) }



    val titleError = mutableStateOf<String?>(null)
    val yearError = mutableStateOf<String?>(null)
    val genreError = mutableStateOf<String?>(null)
    val directorError = mutableStateOf<String?>(null)
    val actorsError = mutableStateOf<String?>(null)
    val plotError = mutableStateOf<String?>(null)
    val ratingError = mutableStateOf<String?>(null)

    private val _isFormValid = mutableStateOf(false)
    val isFormValid: State<Boolean>
        get() = _isFormValid



    fun validateTitle(title: String) {
        titleError.value = if (title.isBlank()) "Title cannot be empty" else null
        validateForm()
    }

    fun validateYear(year: String) {
        yearError.value = if (year.isBlank()) "Year cannot be empty" else null
        validateForm()
    }

    fun validateGenres(genres: List<Genre>) {
        genreError.value = if (genres.isEmpty()) "At least one genre must be selected" else null
        validateForm()
    }

    fun validateDirector(director: String) {
        directorError.value = if (director.isBlank()) "Director cannot be empty" else null
        validateForm()
    }

    fun validateActors(actors: String) {
        actorsError.value = if (actors.isBlank()) "Actors cannot be empty" else null
        validateForm()
    }

    fun validatePlot(plot: String) {
        plotError.value = null // No validation for plot
        validateForm()
    }

    fun validateRating(rating: Float?) {
        ratingError.value = if (rating == null) "Rating cannot be empty" else null
        validateForm()
    }

    private fun validateForm() {
        _isFormValid.value = titleError.value == null &&
                yearError.value == null &&
                genreError.value == null &&
                directorError.value == null &&
                actorsError.value == null &&
                ratingError.value == null
    }

    var nextMovieId = 10

    fun addMovie(movie: Movie) {
        movieList.add(movie)
        nextMovieId++ // Inkrementiere die nächste verfügbare ID
    }


    fun toggleFavorite(movieId: String) {
        val index = movieList.indexOfFirst { it.id == movieId }
        if (index != -1) {
            val movie = movieList[index]
            movie.isFavorite = !movie.isFavorite
            movieList[index] = movie
            if (movie.isFavorite) {
                favoriteMovies.add(movieId)
            } else {
                favoriteMovies.remove(movieId)
            }
        }
    }



}



