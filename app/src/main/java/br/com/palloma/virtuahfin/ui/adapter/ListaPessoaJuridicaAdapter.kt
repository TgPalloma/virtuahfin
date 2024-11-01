package br.com.palloma.virtuahfin.ui.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.com.palloma.virtuahfin.R
import br.com.palloma.virtuahfin.dao.PessoaJuridicaDao
import br.com.palloma.virtuahfin.databinding.PessoaJuridicaListItemBinding
import br.com.palloma.virtuahfin.model.PessoaJuridica
import br.com.palloma.virtuahfin.ui.activity.PessoaJuridicaActivity
import br.com.palloma.virtuahfin.util.ConversorDeDatas

class ListaPessoaJuridicaAdapter (
    private val context: Context,
    pessoajuridicas: List<PessoaJuridica>
): RecyclerView.Adapter<ListaPessoaJuridicaAdapter.ViewHolder>() {

    private val pessoasJuridicas = pessoajuridicas.toMutableList()


    class ViewHolder (private val binding: PessoaJuridicaListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun vincula (pessoaJuridica: PessoaJuridica, position: Int, context: Context) {

            val pessoaJuridicaDao = PessoaJuridicaDao()
            val conversorDeDatas = ConversorDeDatas()

            val etNomeFantasia= binding.itemPessoaJuridicaNomeFantasia
            etNomeFantasia.text = pessoaJuridica.nomeFantasia

            val etNomeResponsavel = binding.itemPessoaJuridicaNomeResponsavel
            etNomeResponsavel.text = pessoaJuridica.nomeResponsavel

            val etDataDeCadastro = binding.itemPessoaJuridicaDataCadastro
            etDataDeCadastro.text = conversorDeDatas.converterDataParaString(pessoaJuridica.dataDeCadastro)
            //pessoaJuridica.dataDeCadastro.toString()

            val cardViewLayout = binding.itemPessoaJuridicaCardviewLayout
            defineStatusDoCardView(pessoaJuridica, cardViewLayout)

            val tvStatus = binding.itemPessoaJuridicaStatus
            tvStatus.text = pessoaJuridica.statusToString()

            val cardView = binding.itemPessoaJuridicaCardview
            listenerCardView(cardView, context, position)

            val swStatus = binding.itemPessoaJuridicaSwithStatus
            swStatus.isChecked = pessoaJuridica.status

            swStatus.setOnCheckedChangeListener { _, isChecked ->
                pessoaJuridicaDao.ativaDesativaCadastro(pessoaJuridica, isChecked)
                defineStatusDoCardView(pessoaJuridica, cardViewLayout)
                tvStatus.text = pessoaJuridica.statusToString()
            }
        }

        private fun defineStatusDoCardView(
            pessoaJuridica: PessoaJuridica,
            cardViewLayout: ConstraintLayout
        ) {
            if (pessoaJuridica.status) {
                cardViewLayout.setBackgroundResource(R.drawable.cardview_list_item_actived_backgroud)
            } else {
                cardViewLayout.setBackgroundResource(R.drawable.cardview_list_item_deactived_backgroud)
            }
        }

        private fun listenerCardView(
            cardView: CardView,
            context: Context,
            position: Int
        ) {
            cardView.setOnClickListener {
                val intent = Intent(context, PessoaJuridicaActivity::class.java)
                intent.putExtra("indexPessoaJuridica", position)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val inflater = LayoutInflater.from(context)
        val binding = PessoaJuridicaListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pessoaJuridica = pessoasJuridicas[position]
        holder.vincula(pessoaJuridica, position, context)
    }

    override fun getItemCount(): Int {
        return pessoasJuridicas.size
    }

    fun atualiza (listaAtualizada: List<PessoaJuridica>) {
        this.pessoasJuridicas.clear()
        this.pessoasJuridicas.addAll(listaAtualizada)
        Log.d("ListaNoAdapterAdd", pessoasJuridicas.toString())
        notifyDataSetChanged()
    }
}