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
    var release_dateFrom: String = "",
    var release_dateTo: String = "",
    var genres: MutableList<String> = mutableListOf<String>()
) {

    fun isEmpty() =
        language.isEmpty() && genres.isEmpty() && release_dateFrom.isEmpty() && release_dateTo.isEmpty()

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
            GENRES -> { (value as? String)?.let { this.genres.add(it.lowercase()) } }
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