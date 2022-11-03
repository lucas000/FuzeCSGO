package com.fuze.csgo.other

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Utils {

    companion object {

        fun getDateTime(s: String): String? {
            return try {
                var newDate = ""
                val brazilian = Locale("pt", "BR")
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm E", brazilian)
                val current = LocalDateTime.now().format(formatter)

                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm E", brazilian)
                sdf.timeZone = TimeZone.getTimeZone("UTC")
                var date = s.substring(0, 19).format(formatter, brazilian)

                var dayNow = current.substring(0, 2).toInt()
                var monthNow = current.substring(3, 5).toInt()
                var yearNow = current.substring(6, 10).toInt()
                var dayMatch = date.substring(8, 10).toInt()
                var monthMatch = date.substring(5, 7).toInt()
                var yearMatch = date.substring(0, 4).toInt()
                var dateMonth = Date(yearMatch, monthMatch, dayMatch)

                if(dayNow == dayMatch && monthNow == monthMatch && yearNow == yearMatch) {
                    newDate = "Hoje, ${date.substring(11, 13) + ":" + date.substring(14, 16)}"
                } else if (dayMatch > dayNow && monthNow == monthMatch && yearNow == yearMatch) {
                    val formatDayWeek = SimpleDateFormat("E", brazilian)
                    val finalDay: String = formatDayWeek.format(dateMonth)
                    newDate = "${finalDay.replace(".", "")
                        .replaceFirstChar { 
                            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() 
                        }}, ${date.substring(11, 13) + ":" + date.substring(14, 16)}"
                }
                newDate
            } catch (e: Exception) {
                e.toString()
            }
        }
    }
}