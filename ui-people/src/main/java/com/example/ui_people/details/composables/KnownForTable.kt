package com.example.ui_people.details.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.person.PersonCombinedCredit
import com.example.model.person.PersonCredit
import com.example.model.person.PersonCreditsResponse
import com.example.model.person.PersonCrewCredit
import com.example.moviestmdb.core_ui.util.parseStringToCalender
import java.text.SimpleDateFormat
import java.util.*

class CreditDepartmentTitle(val department: String) : PersonCredit {
    override val titleText: String
        get() = ""
    override val subTitleText: String
        get() = ""
    override val departmentJob: String
        get() = department

}

val PersonCredit.convertedDate
    get() = parseStringToCalender(
        date = (this as? PersonCombinedCredit)?.let { castCredit ->
            castCredit.firstAirDate ?: castCredit.releaseDate
        } ?: (this as? PersonCrewCredit)?.releaseDate,
        formatter = SimpleDateFormat("YYYY-MM-DD")
    )

val PersonCredit.releaseYear
    get() = convertedDate?.get(Calendar.YEAR)

fun PersonCreditsResponse.yearsToCredits() =

    mutableListOf<Pair<String, PersonCredit>>().apply {

        var department = ""

        mutableListOf<Int>().apply {
            this@yearsToCredits.cast.forEach { personCast ->
                personCast.releaseYear?.let { releaseYear ->
                    this.add(releaseYear)
                }
            }
            this@yearsToCredits.crew.forEach { personCast ->
                personCast.releaseYear?.let { releaseYear ->
                    this.add(releaseYear)
                }
            }
        }.toSortedSet().reversed().let { sortedYears ->

            cast.sortedBy {
                it.convertedDate
            }.let { sortedCredits ->
                sortedYears.forEach { year ->
                    sortedCredits.filter {
                        it.releaseYear == year
                    }.map {
                        if (it.departmentJob != department) {
                            department = it.departmentJob
                            add("" to CreditDepartmentTitle(it.departmentJob))
                        }
                        add(year.toString() to it)
                    }
                }
            }

            crew.sortedBy {
                it.convertedDate
            }.let { sortedCredits ->
                sortedYears.forEach { year ->
                    sortedCredits.filter {
                        it.releaseYear == year
                    }.map {
                        if (it.departmentJob != department) {
                            department = it.departmentJob
                            add("" to CreditDepartmentTitle(it.departmentJob))
                        }
                        add(year.toString() to it)
                    }
                }
            }
        }
    }

@Composable
fun KnownForTable(credits: PersonCreditsResponse) {

    var currentDepartment = ""
    var currentYear = remember { mutableStateOf("") }

    Card() {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp),
        ) {
            val data = credits.yearsToCredits()
            items(
                count = data.size,
                itemContent = { index ->

                    data[index].let {
                        when {
                            it.second is CreditDepartmentTitle -> {
                                DepartmentTitleView(it.second as CreditDepartmentTitle)
                            }
                            it.first == currentYear.value ->
                                CreditItem(
                                    false,
                                    data[index].first,
                                    data[index].second
                                )
                            else -> {
                                currentYear.value = it.first
                                CreditItem(
                                    true,
                                    data[index].first,
                                    data[index].second
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun DepartmentTitleView(creditDepartmentTitle: CreditDepartmentTitle) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Divider()
        Text(
            text = creditDepartmentTitle.department,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.background(MaterialTheme.colors.background)
        )
    }
}

@Composable
fun CreditItem(presentYear: Boolean, year: String?, credit: PersonCredit) {
    Box(
        modifier = Modifier.padding(top = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
        ) {
            val yearText = "${year ?: '-'}"
            if (presentYear) {
                Text(text = yearText)
            } else {
                Text(
                    text = yearText,
                    color = Color.Transparent,
                    modifier = Modifier.background(Color.Transparent)
                )
            }
            Row(
                verticalAlignment = Alignment.Top,
            ) {
                Box(
                    Modifier.padding(top = 8.dp)
                ) {
                    DotCircle()
                }
                Column(
                    modifier = Modifier.padding(bottom = 8.dp),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = credit.titleText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .height(18.dp)
                            .padding(bottom = 0.dp, top = 0.dp)
                    )
                    Text(
                        text = credit.subTitleText,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .height(15.dp)
                            .padding(bottom = 0.dp, top = 0.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DotCircle() {
    Canvas(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .size(6.dp)
    ) {
        drawCircle(Color.Black)
    }
}
