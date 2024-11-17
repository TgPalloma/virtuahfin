package br.com.palloma.virtuahfin.util

import android.icu.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class ConversorDeDatas {

    val formatoData = "dd/MM/yyyy"

    fun converterLocalDateParaString (data: LocalDate?): String {
        val formatoString = DateTimeFormatter.ofPattern(formatoData)
        return data?.format(formatoString).toString()
    }

    fun converterCalendarParaString(data: Calendar): String {
        val dateFormat = SimpleDateFormat(formatoData , Locale.getDefault())
        val formattedDate = dateFormat.format(data.time)
        return formattedDate.toString()
    }

    fun converterStringParaLocalDate(dataString: String): LocalDate {
        val dataConvertida = LocalDate.parse(dataString,DateTimeFormatter.ofPattern(formatoData))
        return dataConvertida
    }
}

