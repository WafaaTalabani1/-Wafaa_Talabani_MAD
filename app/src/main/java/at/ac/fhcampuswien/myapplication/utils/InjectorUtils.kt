package at.ac.fhcampuswien.myapplication.utils

import android.content.Context
import at.ac.fhcampuswien.myapplication.data.MovieDatabase
import at.ac.fhcampuswien.myapplication.repositories.MovieRepository
import at.ac.fhcampuswien.myapplication.viewModels.MovieViewModelFactory

object InjectorUtils {
    private fun getMovieRepository(context: Context): MovieRepository{
        return MovieRepository(MovieDatabase.getDatabase(context).movieDao())
    }

    fun provideMovieViewModelFactory(context: Context): MovieViewModelFactory {
        val repository = getMovieRepository(context)
        return MovieViewModelFactory(repository)
    }

}