package at.ac.fhcampuswien.myapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampuswien.myapplication.models.Movie
import at.ac.fhcampuswien.myapplication.models.getMovies
import at.ac.fhcampuswien.myapplication.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieRepository): ViewModel() {
    private val _movie = MutableStateFlow(getMovies()) //listOf<Movie>()
    val movie: StateFlow<List<Movie>> = _movie.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllMovies().collect{ value ->
                if (!value.isNullOrEmpty()){
                    _movie.value = value
                }
            }
        }
    }

    suspend fun toggleFavorite(movie: Movie){
        movie.isFavorite = !movie.isFavorite
        repository.updateMovie(movie)
    }
}
