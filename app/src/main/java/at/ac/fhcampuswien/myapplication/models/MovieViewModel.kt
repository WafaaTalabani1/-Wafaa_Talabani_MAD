package at.ac.fhcampuswien.myapplication.models

import androidx.compose.runtime.MutableState
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
    var nextMovieId = 10

    fun addMovie(movie: Movie) {
        movieList.add(movie)
        nextMovieId++ // Inkrementiere die nächste verfügbare ID
    }



}



