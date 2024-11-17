package br.com.palloma.virtuahfin.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.palloma.virtuahfin.R
import br.com.palloma.virtuahfin.dao.PropostaDao
import br.com.palloma.virtuahfin.databinding.PropostaListItemBinding
import br.com.palloma.virtuahfin.model.Proposta

class ListaPropostaAdapter(propostas: List<Proposta>, private val context: Context) : RecyclerView.Adapter<ListaPropostaAdapter.ViewHolder>() {

    private val listaPropostas = propostas.toMutableList()

    class ViewHolder(private val binding: PropostaListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun vincula (proposta: Proposta, position: Int) {

            val propostaDao = PropostaDao()

            val cardView = binding.itemPropostaCardview
            val imageView = binding.itemPropostaTarjaStatus
            if (!propostaDao.verificaStatus(proposta)) {
                cardView.setBackgroundResource(R.drawable.cardview_list_item_deactived_backgroud)
                imageView.setBackgroundResource(R.color.gray)
            } else {
                cardView.setBackgroundResource(R.drawable.cardview_list_item_actived_backgroud)
                imageView.setBackgroundResource(R.color.yellow_300)
            }

            val etClienteNomeFantasia : TextView = binding.itemPropostaClienteNomeFantasia
            etClienteNomeFantasia.text = proposta.cliente.nomeFantasia

            val etAssistenteNomeResponsavel : TextView = binding.itemPropostaAssistenteNomeResposavel
            etAssistenteNomeResponsavel.text = proposta.assistente.nomeResponsavel

            if (proposta.parceiro != null) {
                val etParceiroNomeResponsavel: TextView = binding.itemPropostaParceiroNomeResponsavel
                etParceiroNomeResponsavel.text = proposta.parceiro!!.nomeResponsavel
                etParceiroNomeResponsavel.visibility = View.VISIBLE
            }

            val etHoraDiaria : TextView = binding.itemPropostaHoraDiaria
            val horasDiaria = proposta.horasDiarias.toString() + " horas"
            etHoraDiaria.text = horasDiaria

            val etStatus = binding.itemPropostaStatus
            etStatus.text = propostaDao.calcularDiasRestantes(proposta)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = PropostaListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val proposta = listaPropostas[position]
        holder.vincula(proposta, position)
    }

    override fun getItemCount(): Int {
        return listaPropostas.size
    }

    fun atualiza(listaPropostas: List<Proposta>) {
        this.listaPropostas.clear()
        this.listaPropostas.addAll(listaPropostas)
        notifyDataSetChanged()
    }
}