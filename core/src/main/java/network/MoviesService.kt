package network

import com.example.model.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/upcoming")
    fun getUpcoming(@Query("page") page: Int): Call<MoviesResponse>

    @GET("movie/popular")
    fun getPopular(@Query("page") page: Int): Call<MoviesResponse>

    @GET("movie/now_playing")
    fun getNowPlaying(@Query("page") page: Int): Call<MoviesResponse>

    @GET("movie/top_rated")
    fun getTopRated(@Query("page") page: Int): Call<MoviesResponse>

}