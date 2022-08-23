package network

import com.example.model.movie.MovieCreditsResponse
import com.example.model.movie.GenreResponse
import com.example.model.movie.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MoviesService {

    @GET("movie/upcoming")
    fun getUpcoming(@Query("page") page: Int): Call<MovieResponse>

    @GET("movie/popular")
    fun getPopular(@Query("page") page: Int): Call<MovieResponse>

    @GET("movie/now_playing")
    fun getNowPlaying(@Query("page") page: Int): Call<MovieResponse>

    @GET("movie/top_rated")
    fun getTopRated(@Query("page") page: Int): Call<MovieResponse>

    @GET("movie/{movie_id}/credits")
    fun getCredits(@Path("movie_id") movieId: Int): Call<MovieCreditsResponse>

    @GET("movie/{movie_id}/recommendations")
    fun getRecommended(@Path("movie_id") movieId: Int): Call<MovieResponse>

    @GET("discover/movie")
    fun getDiscovery(@Query("page") page: Int, @QueryMap queries: Map<String, String>) : Call<MovieResponse>

    @GET("genre/movie/list")
    fun getGenres(): Call<GenreResponse>
}