package db

import androidx.room.TypeConverter
import com.example.model.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ListConverter {
    @TypeConverter
    fun fromMovieList(movie: List<Movie?>?): String? {
        if (movie == null)  return null

        val gson = Gson()
        val type: Type =
            object : TypeToken<List<Movie?>?>() {}.type
        return gson.toJson(movie, type)
    }

    @TypeConverter
    fun toMovieList(movieString: String?): List<Movie>? {
        if (movieString == null) return null

        val gson = Gson()
        val type: Type =
            object : TypeToken<List<Movie?>?>() {}.type
        return gson.fromJson(movieString, type)
    }
}