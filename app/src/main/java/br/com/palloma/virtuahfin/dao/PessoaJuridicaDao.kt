package br.com.palloma.virtuahfin.dao

import android.util.Log
import br.com.palloma.virtuahfin.model.PessoaJuridica
import br.com.palloma.virtuahfin.model.TipoContrato

public class PessoaJuridicaDao {

    companion object {
        private var listaPessoaJuridica = mutableListOf<PessoaJuridica>()
    }

    fun salvar (pessoaJuridica: PessoaJuridica) {
        listaPessoaJuridica.add(pessoaJuridica)
    }

    fun atualizar (pessoaJuridica: PessoaJuridica, index: Int) {
        listaPessoaJuridica[index] = pessoaJuridica
        Log.d("atualizandoPessoaJuridica", pessoaJuridica.toString())
    }

    fun excluir(pessoaJuridica: PessoaJuridica) {
        listaPessoaJuridica.remove(pessoaJuridica)
    }

    fun acessarLista() : List<PessoaJuridica> {
        val listaAtualizada = listaPessoaJuridica
        return listaAtualizada.toList()
    }

    fun desativarCadastro(pessoaJuridica: PessoaJuridica) {
        val indexlista = listaPessoaJuridica.indexOf(pessoaJuridica)
        Log.d("indiceDeLista", indexlista.toString())
        listaPessoaJuridica[indexlista].status = false
    }

    //Temporário
    fun ativaDesativaCadastro (pessoaJuridica: PessoaJuridica, status: Boolean) {
        val index = listaPessoaJuridica.indexOf(pessoaJuridica)
        listaPessoaJuridica[index].status = status
    }

    fun listaPorTipoDeContrato (tipoContrato: TipoContrato): MutableList<PessoaJuridica> {

        val listaPorTipo = mutableListOf<PessoaJuridica>()

        for(pessoajuridica in listaPessoaJuridica) {

            if (pessoajuridica.tipo == TipoContrato.ADMINISTRATOR)
                listaPorTipo.add(pessoajuridica)

            if (pessoajuridica.tipo == tipoContrato && pessoajuridica.status)
                listaPorTipo.add(pessoajuridica)
        }
        return listaPorTipo
    }
}