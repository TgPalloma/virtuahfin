package br.com.palloma.virtuahfin.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.palloma.virtuahfin.R
import br.com.palloma.virtuahfin.dao.PessoaJuridicaDao
import br.com.palloma.virtuahfin.databinding.ActivityCadastrarPropostaBinding
import br.com.palloma.virtuahfin.model.PessoaJuridica
import br.com.palloma.virtuahfin.model.TipoContrato
import com.google.android.material.textfield.TextInputLayout

class CadastrarPropostaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastrarPropostaBinding.inflate(layoutInflater)
    }

    private val pessoaJuridicaDao = PessoaJuridicaDao()

    private val listaClientes = ArrayList<PessoaJuridica>()
    private val listaAssistentes = ArrayList<PessoaJuridica>()
    private val listaParceiros = ArrayList<PessoaJuridica>()

    private lateinit var actvClientes: AutoCompleteTextView
    private lateinit var actvAssistentes: AutoCompleteTextView
    private lateinit var actvParceiros: AutoCompleteTextView

    private lateinit var checkParceiro: CheckBox

    private lateinit var tilParceiro: TextInputLayout
    private lateinit var tilValorParceiro: TextInputLayout

    private lateinit var etValorCliente: EditText
    private lateinit var etValorAssistente: EditText
    private lateinit var etValoParceiro: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        configuraViews()
        configuraAdaptersNasViews()
        configuraListas()
        checkSwitchParceiro()
    }

    override fun onResume() {
        super.onResume()
        configuraAdaptersNasViews()
        configuraListas()
    }

    private fun checkSwitchParceiro() {
        checkParceiro.setOnCheckedChangeListener { _, isChecked ->
            configuraViewsParceiro(isChecked)
        }
    }

    private fun configuraListas() {
        configiuraListaPorTipo(listaClientes, TipoContrato.CLIENTE)
        configiuraListaPorTipo(listaAssistentes, TipoContrato.ASSISTENTE)
        configiuraListaPorTipo(listaParceiros, TipoContrato.PARCEIRO)
    }

    private fun configuraViews() {
        actvClientes = binding.cadastroPropostaAutoCompleteCliente
        actvAssistentes = binding.cadastroPropostaAutoCompleteAssistente
        actvParceiros = binding.cadastroPropostaAutoCompleteParceiro

        checkParceiro = binding.cadastroPropostaCheckboxParceiro

        tilParceiro = binding.cadastroPropostaTilParceiro
        tilValorParceiro = binding.cadastroPropostaTilValorParceiro

        etValorCliente = binding.cadastroPropostaValorCliente
        etValorAssistente = binding.cadastroPropostaValorAssistente
        etValoParceiro = binding.cadastroPropostaValorParceiro
    }

    private fun configuraAdaptersNasViews() {
        configuraAdapter(listaClientes, actvClientes)
        configuraAdapter(listaAssistentes, actvAssistentes)
        configuraAdapter(listaParceiros, actvParceiros)
    }

    private fun configiuraListaPorTipo(
        listaASerConfigurada: ArrayList<PessoaJuridica>,
        tipoContrato: TipoContrato
    ) {
        listaASerConfigurada.clear()
        listaASerConfigurada.addAll(
            pessoaJuridicaDao.listaPorTipoDeContrato(tipoContrato)
        )
    }

    private fun configuraAdapter (
        listaASerConfigurada: ArrayList<PessoaJuridica>,
        autoCompleteTextView: AutoCompleteTextView
    ) {
        val arrayAdapter = ArrayAdapter(this, R.layout.list_item, listaASerConfigurada)
        autoCompleteTextView.setAdapter(arrayAdapter)
    }

    private fun configuraViewsParceiro (visivel: Boolean) {
        if (visivel){
            tilParceiro.visibility = View.VISIBLE
            tilValorParceiro.visibility = View.VISIBLE
        } else {
            tilParceiro.visibility = View.GONE
            tilValorParceiro.visibility = View.GONE
        }
    }
}