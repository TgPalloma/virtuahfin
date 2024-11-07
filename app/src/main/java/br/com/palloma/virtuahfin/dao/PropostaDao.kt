package br.com.palloma.virtuahfin.dao

import br.com.palloma.virtuahfin.model.PessoaJuridica
import br.com.palloma.virtuahfin.model.Proposta

class PropostaDao {

    companion object {
        private val listaPropostas = mutableListOf<Proposta>()
    }

    fun salvarProposta (proposta: Proposta) {
        listaPropostas.add(proposta)
    }

}