package com.example.model.tvshow

import com.example.model.Genre
import com.example.model.person.TvShowCreator

data class TvShowDetails(
    val backdrop_path: String? = null,
    val created_by: List<TvShowCreator> = listOf(),
    val episode_run_time: List<Int>? = null,
    val first_air_date: String? = null,
    val genres: List<Genre> = listOf(),
    val homepage: String? = null,
    val id: Int? = null,
    val in_production: Boolean? = null,
    val languages: List<String> = listOf(),
    val last_air_date: String? = null,
    val last_episode_to_air: TvShowLastEpisodeOnAir? = null,
    val name: String? = null,
    val next_episode_to_air: Any? = null,
    val networks: List<TvShowsNetwork> = listOf(),
    val number_of_episodes: Int? = null,
    val number_of_seasons: Int? = null,
    val origin_country: List<String> = listOf(),
    val original_language: String? = null,
    val original_name: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val poster_path: String? = null,
    val production_companies: List<TvShowProductionCompany> = listOf(),
    val production_countries: List<TvShowProductionCountries> = listOf(),
    val seasons: List<TvShowsSeasons> = listOf(),
    val spoken_languages: List<TvShowsSpokenLanguages> = listOf(),
    val status: String? = null,
    val tagline: String? = null,
    val type: String? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null
)