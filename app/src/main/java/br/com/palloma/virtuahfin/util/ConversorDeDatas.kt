package br.com.palloma.virtuahfin.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ConversorDeDatas {

    private val formatoString: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    fun converterDataParaString (data: LocalDate?): String {
        return data?.format(formatoString).toString()
    }
}

