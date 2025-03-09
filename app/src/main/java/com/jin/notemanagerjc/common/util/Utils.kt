package com.jin.notemanagerjc.common.util

import java.util.Calendar

fun Long.toTimeFormat(): String {
    val calendar = Calendar.getInstance().apply { timeInMillis = this@toTimeFormat }
    var rs = ""
    val hours = calendar.get(Calendar.HOUR_OF_DAY)
    val minutes = calendar.get(Calendar.MINUTE)
    val second = calendar.get(Calendar.SECOND)

    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val month = calendar.get(Calendar.MONTH) + 1
    val year = calendar.get(Calendar.YEAR)

    rs += hours.formatNumber()
    rs += ":"
    rs += minutes.formatNumber()
    rs += ":"
    rs += second.formatNumber()
    rs += " "
    rs += day.formatNumber()
    rs += "/"
    rs += month.formatNumber()
    rs += "/"
    rs += year.formatNumber()
    return rs
}

fun Int.formatNumber(): String {
    return if (this < 10) {
        "0$this"
    } else this.toString()
}