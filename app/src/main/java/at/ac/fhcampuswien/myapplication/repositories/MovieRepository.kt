package at.ac.fhcampuswien.myapplication.repositories

import at.ac.fhcampuswien.myapplication.data.MovieDao
import at.ac.fhcampuswien.myapplication.models.Movie
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {
    suspend fun addMovie(movie: Movie) = movieDao.add(movie = movie)
    suspend fun updateMovie(movie: Movie) = movieDao.update(movie = movie)
    suspend fun deleteMove(movie: Movie) = movieDao.delete(movie = movie)
    fun getAllMovies() : Flow<List<Movie>> = movieDao.getAllMovie()
    fun getFavoriteMoves() : Flow<List<Movie>> = movieDao.getAllFavoriteMovie()
    fun getById(id: Int) : Flow<Movie?> = movieDao.getMovieById(id)

}
