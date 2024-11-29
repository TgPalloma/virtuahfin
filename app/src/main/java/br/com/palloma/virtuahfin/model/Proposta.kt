package br.com.palloma.virtuahfin.model

import java.io.Serializable
import java.time.LocalDate

class Proposta(
    val cliente: PessoaJuridica,
    val assistente: PessoaJuridica,
    val valorCliente: Float,
    val valorAssistente: Float,
    val descricaoServico: String,
    val dataInicio: LocalDate,
    val horasDiarias: Int,
    val formaPagamento: FormaDePagamento
) : Serializable {

    var parceiro: PessoaJuridica? = null
    var valorParceiro: Float? = null

    var dataFinalPrevista: LocalDate? = null
    var dataFinalizaçao: LocalDate? = null

    override fun toString(): String {
        return "Proposta(cliente=$cliente, assistente=$assistente, valorCliente=$valorCliente,\n" +
                "valorAssistente=$valorAssistente, descricaoServico='$descricaoServico',\n" +
                "dataInicio=$dataInicio, horasDiarias=$horasDiarias, formaPagamento=$formaPagamento,\n" +
                "parceiro=$parceiro, valorParceiro=$valorParceiro, dataFinalPrevista=$dataFinalPrevista,\n" +
                "dataFinalizaçao=$dataFinalizaçao)"
    }


}