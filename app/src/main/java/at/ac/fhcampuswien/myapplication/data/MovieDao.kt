package at.ac.fhcampuswien.myapplication.data

import androidx.room.*
import at.ac.fhcampuswien.myapplication.models.Movie
import kotlinx.coroutines.flow.Flow
@Dao
interface MovieDao {

    @Insert
    suspend fun add(movie : Movie)

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)


    @Query("SELECT * FROM Movie")
    fun getAllMovie(): Flow<List<Movie>>

    @Query("Select * FROM Movie WHERE id=:movieId")
    fun getMovieById(movieId: Int): Flow<Movie?>

  @Query("Select * FROM Movie WHERE isFavorite = 1")
    fun getAllFavoriteMovie() : Flow<List<Movie>>
}