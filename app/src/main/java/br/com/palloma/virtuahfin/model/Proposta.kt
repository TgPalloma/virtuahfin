package br.com.palloma.virtuahfin.model

class Proposta(
    val cliente: PessoaJuridica,
    val assistente: PessoaJuridica,
    val valorCliente: Double,
    val valorAssistente: Double,
) {

    private var parceiro: PessoaJuridica? = null
        get
    private var valorParceiro: Double? = null
        get

    constructor(
        cliente: PessoaJuridica,
        assistente: PessoaJuridica,
        valorCliente: Double,
        valorAssistente: Double,
        parceiro: PessoaJuridica,
        valorParceiro: Double): this(
        cliente, assistente, valorCliente, valorAssistente
    ) {
            this.parceiro = parceiro
            this.valorParceiro = valorParceiro
        }
}