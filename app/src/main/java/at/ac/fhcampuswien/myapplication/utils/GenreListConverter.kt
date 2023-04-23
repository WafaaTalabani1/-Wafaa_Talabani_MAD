package at.ac.fhcampuswien.myapplication.utils

import androidx.room.TypeConverter
import at.ac.fhcampuswien.myapplication.models.Genre

class GenreListConverter {

    @TypeConverter
    fun fromGenres(genres: List<Genre>): String {
        return genres.joinToString(separator = ",") { it.name }
    }

    @TypeConverter
    fun toGenres(genresString: String): List<Genre> {
        return genresString.split(",").map { Genre.valueOf(it.trim()) }
    }
}
