package br.com.palloma.virtuahfin.dao

import br.com.palloma.virtuahfin.model.Proposta
import br.com.palloma.virtuahfin.util.ConversorDeDatas
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class PropostaDao {

    companion object {
        private val listaPropostas = mutableListOf<Proposta>()
    }

    fun salvarProposta(proposta: Proposta) {
        listaPropostas.add(proposta)
    }

    fun acessaLista(): List<Proposta> {
        val listaAtualizada = listaPropostas
        return listaAtualizada.toList()
    }

    fun verificaStatus(proposta: Proposta): Boolean {
        return proposta.dataFinalizaçao == null
        //Se null = proposta ativa (true)
    }

    fun calcularDiasRestantes(proposta: Proposta): String  {

        if (verificaStatus(proposta)) {

            if(proposta.dataInicio.isBefore(LocalDate.now())) {
                val dataDeInicio = ConversorDeDatas().converterLocalDateParaString(proposta.dataInicio)
                return "Proposta será iniciada em $dataDeInicio"
            }
            if (proposta.dataFinalPrevista != null) {
                val diasRestantes = LocalDate.now()!!.until(proposta.dataFinalPrevista, ChronoUnit.DAYS).toInt()
                return "Encerra em $diasRestantes corridos"

            } else {
                return "Sem previsão de Encerramento (Proposta Ativa)"

            }
        } else {
            val dataFinalizada = ConversorDeDatas().converterLocalDateParaString(proposta.dataFinalizaçao)
            return "Proposta finalizada em $dataFinalizada"

        }
    }
}