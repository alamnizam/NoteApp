package com.codeturtle.notes.common.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

object HandleDate {
    @SuppressLint("SimpleDateFormat")
    fun convertLongToDate(timeStamp: Long): String {
        val date = Date(timeStamp * 1000)
        val format = SimpleDateFormat("dd-MMM-yyyy")
        return format.format(date)
    }
}