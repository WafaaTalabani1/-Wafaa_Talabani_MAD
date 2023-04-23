package at.ac.fhcampuswien.myapplication.viewModels

import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.myapplication.models.Movie
import at.ac.fhcampuswien.myapplication.repositories.MovieRepository

class AddMovieViewModel(private val repository: MovieRepository): ViewModel(){
   suspend fun addMovie(movie:Movie){
       repository.addMovie(movie)
   }
    fun validateUserInput(input: String): Boolean{
        return input.isNotBlank()
    }
}