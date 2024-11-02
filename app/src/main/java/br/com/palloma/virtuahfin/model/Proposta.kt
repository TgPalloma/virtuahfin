package br.com.palloma.virtuahfin.model

import java.time.LocalDate

class Proposta(
    val cliente: PessoaJuridica,
    val assistente: PessoaJuridica,
    val valorCliente: Double,
    val valorAssistente: Double,
    val descricaoServico: String,
    val dataInicio: LocalDate,
    val dataFinalPrevista: LocalDate,
    val horasDiarias: Int,
    val formaPagamento: FormaDePagamento
) {

    private var parceiro: PessoaJuridica? = null
        get
    private var valorParceiro: Double? = null
        get

    private var dataFinalizaçao: LocalDate? = null
        get set

    constructor(
        cliente: PessoaJuridica,
        assistente: PessoaJuridica,
        valorCliente: Double,
        valorAssistente: Double,
        parceiro: PessoaJuridica,
        valorParceiro: Double,
        descricaoServico: String,
        dataInicio: LocalDate,
        dataFinalPrevista: LocalDate,
        horasDiarias: Int,
        formaPagamento: FormaDePagamento): this(
        cliente, assistente, valorCliente, valorAssistente, descricaoServico, dataInicio, dataFinalPrevista,
        horasDiarias, formaPagamento
    ) {
            this.parceiro = parceiro
            this.valorParceiro = valorParceiro
    }
}