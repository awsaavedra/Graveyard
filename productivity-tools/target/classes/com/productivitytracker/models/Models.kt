package com.productivitytracker.models

data class DailyEntry(
    val date: String,  // YYYY-MM-DD
    val hoursLogged: Int = 0  // 0-8 hours
)
