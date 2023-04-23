package at.ac.fhcampuswien.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import at.ac.fhcampuswien.myapplication.utils.GenreListConverter
import at.ac.fhcampuswien.myapplication.utils.StringListConverter
import at.ac.fhcampuswien.myapplication.models.Movie


@Database(
    entities = [Movie::class],
    version = 2,
    exportSchema = false
)

@TypeConverters(StringListConverter::class, GenreListConverter::class)

abstract class MovieDatabase: RoomDatabase() {
    // Dao instance so that the DB knows about the Dao
    // add more daos here if you have multiple tables
    abstract fun movieDao(): MovieDao


    // declare as singleton - companion objects are like static variables in Java
    companion object {
        @Volatile // never cache the value of Instance
        private var Instance: MovieDatabase? = null
        fun getDatabase(context: Context): MovieDatabase{
            return Instance ?: synchronized(this) { // wrap in synchronized block to prevent race conditions
                Room.databaseBuilder(context, MovieDatabase::class.java, "movie_db")
                    .fallbackToDestructiveMigration() // if schema changes wipe the whole schema - you could add your migration strategies here
                    .build() // create an instance of the db
                    .also {
                        Instance = it
                    } }
        } }
}