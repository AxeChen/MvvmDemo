package com.axe.libcommon.ext

import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

/**
 * 这个拓展封装了一些时间格式化的类
 */

val DEFAULT_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE)

const val MINUTE_SECOND = 60

const val HOUR_SECOND = 3600

const val DAY_SECOND = 86400


fun formatDate(date: Long, format: SimpleDateFormat = DEFAULT_DATE_FORMAT): String {
    return format.format(Date(if (date.toString().length < 13) date * 1000 else date))
}

fun formatDate(date: String, format: SimpleDateFormat = DEFAULT_DATE_FORMAT): String {
    return format.format(date)
}

fun formatDate(date: Date, format: SimpleDateFormat = DEFAULT_DATE_FORMAT): String {
    return format.format(date)
}

fun stringToDate(time: String, format: SimpleDateFormat = DEFAULT_DATE_FORMAT):Date {
    return format.parse(time) ?: Date(0)
}

/**
 * 格式化当前日期
 */
fun formatCurrentDate(): String {
    return formatDate(
        Date(),
        SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE)
    )
}

fun formatTime(time: Int): String {
    val day = time / DAY_SECOND
    if (day > 0) {
        return "${day}天"
    }
    val hour = (time / HOUR_SECOND) % 24
    if (hour > 0) {
        return "${hour}小时"
    }
    val minute = (time / MINUTE_SECOND) % 60
    return "${minute}分钟"
}

fun formatDiffTime(time: Long): String {
    val lastTime = if (time.toString().length < 13) time else time / 1000
    val diffTime = System.currentTimeMillis() / 1000 - lastTime
    val day = diffTime / DAY_SECOND
    if (day > 0) {
        return "${day}天前"
    }
    val hour = (diffTime / HOUR_SECOND) % 24
    if (hour > 0) {
        return "${hour}小时前"
    }
    val minute = (diffTime / MINUTE_SECOND) % 60
    if (minute > 0) {
        return "${minute}分钟前"
    }
    return "刚刚"
}

fun formatSecondHour(secondTime: Long): String{
    return formatSecondHour(secondTime.div(1000).toInt())
}

fun formatSecondHour(secondTime: Int): String{
    val formatString = StringBuilder()
    if(secondTime <= 0){
        formatString.append("00:00:00")
    }else{
        val hour = (secondTime / HOUR_SECOND) % 24
        val minute = (secondTime / MINUTE_SECOND) % 60
        val second = secondTime % 60
        formatString.append(unitFormat(hour))
        formatString.append(":")
        formatString.append(unitFormat(minute))
        formatString.append(":")
        formatString.append(unitFormat(second))
    }
    return formatString.toString()
}

fun unitFormat(i: Int): String {
    return if (i in 0..9) "0$i" else i.toString()
}