package br.com.palloma.virtuahfin.util

import android.icu.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class ConversorDeDatas {

    fun converterLocalDateParaString (data: LocalDate?): String {
        val formatoString = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return data?.format(formatoString).toString()
    }

    fun converterCalendarParaString(data: Calendar): String {
        val dateFormat = SimpleDateFormat( "dd/MM/yyyy" , Locale.getDefault())
        val formattedDate = dateFormat.format(data.time)
        return formattedDate.toString()
    }
}

