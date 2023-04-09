package at.ac.fhcampuswien.myapplication.viewModels

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.myapplication.models.Movie
import at.ac.fhcampuswien.myapplication.models.getMovies

class MovieViewModel: ViewModel() {
    private val _movieList = getMovies().toMutableStateList()
    val movieList: List<Movie>
        get() = _movieList


    fun getMovieById(movieId: String?): Movie {
        return _movieList.filter { it.id == movieId}[0]
    }

    fun getFavoriteMovies(): List<Movie> {
        return _movieList.filter { it.isFavorite }
    }

    fun toggleFavorite(movie: Movie){
        _movieList.find { it.id == movie.id }?.let { movie.isFavorite = !movie.isFavorite }
    }

    var nextMovieId = 10

    fun addMovie(movie: Movie) {
        _movieList.add(movie)
        nextMovieId++ // Inkrementiere die nächste verfügbare ID
    }

    fun validateUserInput(input: String): Boolean{
            return input.isNotBlank()
    }




}


