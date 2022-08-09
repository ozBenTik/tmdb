package com.example.model

import com.example.model.FilterKey.*

enum class FilterKey {
    LANGUAGE,
    GENRES,
    RELEASE_DATE_FROM,
    RELEASE_DATE_TO
}

data class FilterParams(
    var language: String = "",
    var release_dateFrom: Pair<String, Long> = "" to 0L,
    var release_dateTo: Pair<String, Long> = "" to 0L,
    var genres: MutableList<Genre> = mutableListOf()
) {

    override fun equals(other: Any?): Boolean {
        return (other as? FilterParams)?.let { otherFilters ->

            val equalGens = genres.size == otherFilters.genres.size &&
                        genres.containsAll(otherFilters.genres) &&
                        otherFilters.genres.containsAll(genres)

            val equalLang = language == otherFilters.language
            val equal_date_to = release_dateTo == otherFilters.release_dateTo
            val equal_date_from = release_dateFrom == otherFilters.release_dateFrom

            equalGens && equalLang && equal_date_to && equal_date_from
        } ?: false
    }

    fun clear() {
        language = ""
        release_dateFrom = "" to 0L
        release_dateTo = "" to 0L
        genres = mutableListOf()
    }

    fun isEmpty() =
        language.isEmpty() && genres.isEmpty() && release_dateFrom.first.isEmpty() && release_dateTo.first.isEmpty()

    fun removeFilter(filterParam: FilterKey, value: Any) {
        when (filterParam) {
            LANGUAGE -> this.language = ""

            GENRES -> {
                (value as? Int)?.let { idToBeRemoved ->
                    genres.find { it.id == idToBeRemoved }?.let {
                        genres.remove(it)
                    }
                }
            }

            RELEASE_DATE_FROM -> this.release_dateFrom = "" to 0L

            RELEASE_DATE_TO -> this.release_dateTo = "" to 0L
        }
    }

    fun addFilter(filterParam: FilterKey, value: Any) {
        when (filterParam) {
            LANGUAGE -> this.language = (value as? String) ?: ""

            GENRES -> (value as? Genre)?.let {
                if (!genres.contains(it)) {
                    this.genres.add(it)
                }
            }

            RELEASE_DATE_FROM -> this.release_dateFrom =
                (value as? Pair<String, Long>) ?: ("" to 0L)

            RELEASE_DATE_TO -> this.release_dateTo = (value as? Pair<String, Long>) ?: ("" to 0L)
        }
    }

    fun toMap(): Map<String, String> =
        mutableMapOf<String, String>().apply {
            put("release_date.gte", this@FilterParams.release_dateFrom.first)
            put("release_date.lte", this@FilterParams.release_dateTo.first)
            put("language", this@FilterParams.language)
            put("with_genres", this@FilterParams.genres.takeIf { it.isNotEmpty() }?.let {

                mutableListOf<Int>().apply {
                    this@FilterParams.genres.map { genre ->
                        genre.id?.let { genreId -> add(genreId) }
                    }
                }.let { genreIdsList ->
                    val string = genreIdsList.toString()
                    string.substring(1, string.length - 1)
                }
            } ?: "")
        }
}