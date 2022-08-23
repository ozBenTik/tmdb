package com.example.moviestmdb.core_ui.util

import com.google.android.material.datepicker.MaterialDatePicker
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun parseDateToString(timeMiles: Long, formatter: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US)): String = run {
    val calender = Calendar.getInstance()
    calender.timeInMillis =
        timeMiles.takeIf { it > 0 } ?: MaterialDatePicker.todayInUtcMilliseconds()
    formatter.format(calender.time)
}

fun parseStringToCalender(date: String?, formatter: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US)): Calendar? = run {
    return Calendar.getInstance().takeIf { date != null }?.apply {
        try {
            time = formatter.parse(date!!)
        } catch (e: ParseException) {
            e.printStackTrace();
        }
    }
}