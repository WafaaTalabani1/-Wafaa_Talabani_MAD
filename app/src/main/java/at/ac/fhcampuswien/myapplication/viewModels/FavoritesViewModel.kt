package at.ac.fhcampuswien.myapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampuswien.myapplication.models.Movie
import at.ac.fhcampuswien.myapplication.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repository: MovieRepository): ViewModel(){
    private val _movieListState = MutableStateFlow(listOf<Movie>())
    val movieListState : StateFlow<List<Movie>> = _movieListState.asStateFlow()

    init{
        viewModelScope.launch{
            repository.getFavoriteMoves().collect{
                listOfMovie -> _movieListState.value = listOfMovie

            }
        }
    }
    suspend fun updateFavorite(movie : Movie ){
        movie.isFavorite = !movie.isFavorite
        repository.updateMovie(movie)
    }
}