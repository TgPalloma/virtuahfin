package br.com.palloma.virtuahfin.model

import java.time.LocalDate

class PessoaJuridica(
    val nomeFantasia: String = "",
    val razaoSocial: String,
    val cnpj: String,
    val nomeResponsavel: String,
    val telefone: Long,
    val email: String,
    val tipo: TipoContrato,
    val dataDeCadastro: LocalDate? = LocalDate.now(),
    var status : Boolean = true
) {

    fun statusToString() :String{
        val statusString = if (status) {
            "ATIVO"
        } else {
            "INATIVO"
        }
        return statusString
    }

    override fun toString(): String {
        return nomeFantasia
    }

//    override fun toString(): String {
//        return "PessoaJuridica\n" +
//                "nomeFantasia='$nomeFantasia'\n" +
//                "razaoSocial='$razaoSocial', cnpj='$cnpj'\n" +
//                "nomeContato='$nomeResponsavel', telefone=$telefone\n" +
//                "email='$email', tipo=$tipo, " +
//                "dataDeCadastro='$dataDeCadastro', " +
//                "status= '$status'"
//    }
}