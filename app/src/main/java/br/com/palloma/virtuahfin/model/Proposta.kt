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

    private var parceiro: PessoaJuridica? = null
        get set
    private var valorParceiro: Double? = null
        get set

    private var dataFinalPrevista: LocalDate? = null
        get set
    private var dataFinalizaçao: LocalDate? = null
        get set
}