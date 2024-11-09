package br.com.palloma.virtuahfin.dao

import android.util.Log
import br.com.palloma.virtuahfin.model.PessoaJuridica
import br.com.palloma.virtuahfin.model.TipoContrato

class PessoaJuridicaDao {

    companion object {
        private val listaPessoasJuridicas = mutableListOf<PessoaJuridica>()
    }

    fun salvar (pessoaJuridica: PessoaJuridica) {
        listaPessoasJuridicas.add(pessoaJuridica)
    }

    fun atualizar (pessoaJuridica: PessoaJuridica, index: Int) {
        listaPessoasJuridicas[index] = pessoaJuridica
        Log.d("atualizandoPessoaJuridica", pessoaJuridica.toString())
    }

    fun excluir(pessoaJuridica: PessoaJuridica) {
        listaPessoasJuridicas.remove(pessoaJuridica)
    }

    fun buscarPeloCNPJ (cnpj: String): PessoaJuridica? {
        for (pj in listaPessoasJuridicas) {
            if (cnpj == pj.cnpj) {
                return pj
            }
        }
        return null
    }

    fun acessarLista() : List<PessoaJuridica> {
        val listaAtualizada = listaPessoasJuridicas
        return listaAtualizada.toList()
    }

    fun desativarCadastro(pessoaJuridica: PessoaJuridica) {
        val indexlista = listaPessoasJuridicas.indexOf(pessoaJuridica)
        Log.d("indiceDeLista", indexlista.toString())
        listaPessoasJuridicas[indexlista].status = false
    }

    //Temporário
    fun ativaDesativaCadastro (pessoaJuridica: PessoaJuridica, status: Boolean) {
        val index = listaPessoasJuridicas.indexOf(pessoaJuridica)
        listaPessoasJuridicas[index].status = status
    }

    fun listaPorTipoDeContrato (tipoContrato: TipoContrato): MutableList<PessoaJuridica> {

        val listaPorTipo = mutableListOf<PessoaJuridica>()

        for(pessoajuridica in listaPessoasJuridicas) {

            if (pessoajuridica.tipo == TipoContrato.ADMINISTRATOR)
                listaPorTipo.add(pessoajuridica)

            if (pessoajuridica.tipo == tipoContrato && pessoajuridica.status)
                listaPorTipo.add(pessoajuridica)
        }
        return listaPorTipo
    }
}