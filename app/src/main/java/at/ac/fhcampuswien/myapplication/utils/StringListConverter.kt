package at.ac.fhcampuswien.myapplication.utils

import androidx.room.TypeConverter

class StringListConverter {

    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return list.joinToString(separator = ",")
    }

    @TypeConverter
    fun toStringList(listString: String): List<String> {
        return listString.split(",").map { it.trim() }
    }
}
