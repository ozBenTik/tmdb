package network

import com.example.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/upcoming")
    fun getUpcoming(@Query("page") page: Int): Call<MovieResponse>

    @GET("movie/popular")
    fun getPopular(@Query("page") page: Int): Call<MovieResponse>

    @GET("movie/now_playing")
    fun getNowPlaying(@Query("page") page: Int): Call<MovieResponse>

    @GET("movie/top_rated")
    fun getTopRated(@Query("page") page: Int): Call<MovieResponse>

}