package com.codeturtle.notes.common.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object HandleDate {
    fun convertLongToDate(timeStamp: Long): String {
        val date = Date(timeStamp * 1000)
        val format = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        return format.format(date)
    }
}
