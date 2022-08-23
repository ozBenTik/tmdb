package com.example.model.person

import com.google.gson.annotations.SerializedName

data class PersonCreditsResponse (
    @SerializedName("id")
    val id:Int = 0,

    @SerializedName("cast")
    val cast: List<PersonCombinedCredit> = listOf(),

    @SerializedName("crew")
    val crew: List<PersonCrewCredit> = listOf(),
) {
    val sumCredits
    get() = cast.size + crew.size

    companion object  {
        val Empty = PersonCreditsResponse()
    }
}