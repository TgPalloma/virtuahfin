package br.com.palloma.virtuahfin.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val formatoString: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

fun converterLocalDateParaString (data: LocalDate?): String {
    return data?.format(formatoString).toString()
}