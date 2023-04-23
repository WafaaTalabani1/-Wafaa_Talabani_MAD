package at.ac.fhcampuswien.myapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampuswien.myapplication.models.Movie
import at.ac.fhcampuswien.myapplication.models.getMovies
import at.ac.fhcampuswien.myapplication.repositories.MovieRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: MovieRepository): ViewModel() {
        private val _movieListState = MutableStateFlow(listOf<Movie>())
        val movieListState : StateFlow<List<Movie>> = _movieListState.asStateFlow()

        init{
            viewModelScope.launch{
               repository.getById(movieListState.value.size)
            }
        }

    fun getMovieById(movieId: Int): Flow<Movie?> {
        if (movieId !== null){
            return repository.getById(movieId)
        }
        throw java.lang.IllegalArgumentException("Movie needs to have an ID")
    }


    suspend fun toggleIsFavorite(movie: Movie){
        movie.isFavorite = !movie.isFavorite
        repository.updateMovie(movie)
    }
}