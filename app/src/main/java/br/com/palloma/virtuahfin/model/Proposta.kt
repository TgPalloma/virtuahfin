package br.com.palloma.virtuahfin.model

import java.time.LocalDate

class Proposta(
    val cliente: PessoaJuridica,
    val assistente: PessoaJuridica,
    val valorCliente: Double,
    val valorAssistente: Double,
    val descricaoServico: String,
    val dataInicio: LocalDate,
    val horasDiarias: Int,
    val formaPagamento: FormaDePagamento
) {

    var parceiro: PessoaJuridica? = null
    var valorParceiro: Double? = null

    var dataFinalPrevista: LocalDate? = null
    var dataFinalizaçao: LocalDate? = null

    var statusAtivo: Boolean? = null
}