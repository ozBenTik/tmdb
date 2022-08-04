package com.example.model

import com.example.model.FilterKey.*

enum class FilterKey{
    LANGUAGE,
    GENRES,
    RELEASE_DATE_FROM,
    RELEASE_DATE_TO
}

data class FilterParams(

    var language: String = "",
//pattern: ([a-z]{2})-([A-Z]{2})
//default: en-US
//optional
//
//    val region: String = "",
////Specify a ISO 3166-1 code to filter release dates. Must be uppercase.
////pattern: ^[A-Z]{2}$
////optional
//
//    val sort_by: String = "",
////Allowed Values: , popularity.asc, popularity.desc, release_date.asc, release_date.desc, revenue.asc, revenue.desc, primary_release_date.asc, primary_release_date.desc, original_title.asc, original_title.desc, vote_average.asc, vote_average.desc, vote_count.asc, vote_count.desc
////default: popularity.desc

    var with_genres: String = "",
//Comma separated value of genre ids that you want to include in the results.
//optional

    var release_dateFrom: String = "",
    var release_dateTo: String = "",

    var genres: MutableList<String> = mutableListOf<String>()

////Choose from one of the many available sort options.
////Allowed Values: , popularity.asc, popularity.desc, release_date.asc, release_date.desc, revenue.asc, revenue.desc, primary_release_date.asc, primary_release_date.desc, original_title.asc, original_title.desc, vote_average.asc, vote_average.desc, vote_count.asc, vote_count.desc
////default: popularity.desc
//val certification_country: String = "",
////Used in conjunction with the certification filter, use this to specify a country with a valid certification.
////optional
//val certification: String = "",
////Filter results with a valid certification from the 'certification_country' field.
////optional
//val certification.val lte: String = "",
//Filter and only include movies that have a certification that is less than or equal to the specified value.
//optional
//certification.gte
//String
//Filter and only include movies that have a certification that is greater than or equal to the specified value.
//optional
//include_adult
//boolean
//A filter and include or exclude adult movies.
//default
//optional
//include_video
//boolean
//A filter to include or exclude videos.
//default
//optional
//page
//integer
//Specify the page of results to query.
//minimum: 1
//maximum: 1000
//default: 1
//optional
//primary_release_year
//integer
//A filter to limit the results to a specific primary release year.
//optional
//primary_release_date.gte
//String
//Filter and only include movies that have a primary release date that is greater or equal to the specified value.
//format: date
//optional
//primary_release_date.lte
//String
//Filter and only include movies that have a primary release date that is less than or equal to the specified value.
//format: date
//optional
//release_date.gte
//String
//Filter and only include movies that have a release date (looking at all release dates) that is greater or equal to the specified value.
//format: date
//optional
//release_date.lte
//String
//Filter and only include movies that have a release date (looking at all release dates) that is less than or equal to the specified value.
//format: date
//optional
//with_release_type
//integer
//Specify a comma (AND) or pipe (OR) separated value to filter release types by. These release types map to the same values found on the movie release date method.
//minimum: 1
//maximum: 6
//optional
//year
//integer
//A filter to limit the results to a specific year (looking at all release dates).
//optional
//vote_count.gte
//integer
//Filter and only include movies that have a vote count that is greater or equal to the specified value.
//minimum: 0
//optional
//vote_count.lte
//integer
//Filter and only include movies that have a vote count that is less than or equal to the specified value.
//minimum: 1
//optional
//vote_average.gte
//number
//Filter and only include movies that have a rating that is greater or equal to the specified value.
//minimum: 0
//optional
//vote_average.lte
//number
//Filter and only include movies that have a rating that is less than or equal to the specified value.
//minimum: 0
//optional
//with_cast
//String
//A comma separated list of person ID's. Only include movies that have one of the ID's added as an actor.
//optional
//with_crew
//String
//A comma separated list of person ID's. Only include movies that have one of the ID's added as a crew member.
//optional
//with_people
//String
//A comma separated list of person ID's. Only include movies that have one of the ID's added as a either a actor or a crew member.
//optional
//with_companies
//String
//A comma separated list of production company ID's. Only include movies that have one of the ID's added as a production company.
//optional

//without_genres
//String
//Comma separated value of genre ids that you want to exclude from the results.
//optional
//with_keywords
//String
//A comma separated list of keyword ID's. Only includes movies that have one of the ID's added as a keyword.
//optional
//without_keywords
//String
//Exclude items with certain keywords. You can comma and pipe seperate these values to create an 'AND' or 'OR' logic.
//optional
//with_runtime.gte
//integer
//Filter and only include movies that have a runtime that is greater or equal to a value.
//optional
//with_runtime.lte
//integer
//Filter and only include movies that have a runtime that is less than or equal to a value.
//optional
//with_original_language
//String
//Specify an ISO 639-1 String to filter results by their original language value.
//optional
//with_watch_providers
//String
//A comma or pipe separated list of watch provider ID's. Combine this filter with watch_region in order to filter your results by a specific watch provider in a specific region.
//optional
//watch_region
//String
//An ISO 3166-1 code. Combine this filter with with_watch_providers in order to filter your results by a specific watch provider in a specific region.
//optional
//with_watch_monetization_types
//String
//In combination with watch_region, you can filter by monetization type.
//Allowed Values: flatrate, free, ads, rent, buy
//optional
//without_companies
//String
//Filter the results to exclude the specific production companies you specify here. AND / OR filters are supported.
) {

    fun removeFilter(filterParam: FilterKey, value: Any) {
        when(filterParam) {
            LANGUAGE -> {
                this.language = ""
            }
            GENRES -> {
                (value as? String)?.let {
                    this.genres.remove(it)
                }
            }
            RELEASE_DATE_FROM -> { this.release_dateFrom = "" }
            RELEASE_DATE_TO ->{ this.release_dateTo = "" }
        }
    }

    fun addFilter(filterParam: FilterKey, value: Any) {
        when(filterParam) {
            LANGUAGE -> { this.language = (value as? String) ?: "" }
            GENRES -> { (value as? String)?.let { this.genres.add(it) } }
            RELEASE_DATE_FROM -> { this.release_dateFrom = (value as? String) ?: "" }
            RELEASE_DATE_TO ->{ this.release_dateTo = (value as? String) ?: "" }
        }
    }

    fun toMap(): Map<String, String> =
        mutableMapOf<String, String>().apply {
            put("release_date.gte", this@FilterParams.release_dateFrom)
            put("release_date.lte", this@FilterParams.release_dateTo)
            put("language", this@FilterParams.language)
            put("with_genres", this@FilterParams.genres.takeIf { it.isNotEmpty() }?.let {
                val string = it.toString()
                string.substring(1, string.length-1)
            } ?: "")
        }
}