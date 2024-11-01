package br.com.palloma.virtuahfin.services

import android.content.Context
import android.util.Log
import br.com.palloma.virtuahfin.model.PessoaJuridica

class PessoaJuridicaService(context: Context): Exception() {

    fun verificaPessoaJuridica (pessoaJuridica: PessoaJuridica) {

        check(pessoaJuridica.nomeFantasia.isNotBlank()) {
            throw IllegalStateException("Preencha Nome Fantasia")
        }

        check(pessoaJuridica.razaoSocial.isNotBlank()) {"Precisa de uma razão social"}

        check(pessoaJuridica.cnpj.isNotBlank()) {"Precisa de um CNPJ"}

        check(pessoaJuridica.nomeResponsavel.isNotBlank()) {"Precisa de um Responsável"}

        check(pessoaJuridica.telefone.toString().isNotBlank()) {"Precisa de um nome fantasia"}

        check(pessoaJuridica.email.isNotBlank()) {"Precisa de um e-mail"}
    }
}