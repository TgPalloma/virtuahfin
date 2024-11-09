package br.com.palloma.virtuahfin.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.palloma.virtuahfin.R
import br.com.palloma.virtuahfin.dao.PessoaJuridicaDao
import br.com.palloma.virtuahfin.databinding.ActivityCadastrarPropostaBinding
import br.com.palloma.virtuahfin.model.FormaDePagamento
import br.com.palloma.virtuahfin.model.PessoaJuridica
import br.com.palloma.virtuahfin.model.TipoContrato
import br.com.palloma.virtuahfin.util.ConversorDeDatas
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar

class CadastrarPropostaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastrarPropostaBinding.inflate(layoutInflater)
    }

    private val pessoaJuridicaDao = PessoaJuridicaDao()

    private val listaClientes = ArrayList<PessoaJuridica>()
    private val listaAssistentes = ArrayList<PessoaJuridica>()
    private val listaParceiros = ArrayList<PessoaJuridica>()

    private val listaFormasDePagamento = ArrayList<FormaDePagamento>()
    private val listaHorasDia = listOf(1, 2, 3, 4, 6)

    private lateinit var actvClientes: AutoCompleteTextView
    private lateinit var actvAssistentes: AutoCompleteTextView
    private lateinit var actvParceiros: AutoCompleteTextView
    private lateinit var actvFormaDePagamento: AutoCompleteTextView
    private lateinit var actvHorasDiarias: AutoCompleteTextView

    private lateinit var checkParceiro: CheckBox

    private lateinit var tilParceiro: TextInputLayout
    private lateinit var tilValorParceiro: TextInputLayout
    private lateinit var tilDataDeInicio: TextInputLayout
    private lateinit var tilDataPrevFinal: TextInputLayout

    private lateinit var etValorCliente: EditText
    private lateinit var etValorAssistente: EditText
    private lateinit var etValoParceiro: EditText
    private lateinit var etDataDeInicio: EditText
    private lateinit var etDataPrevFinal: EditText

    private lateinit var btCadastrarProposta: AppCompatButton

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
        configuraListasPessoaJuridica()
        configuraAdaptersNasViews()
        checkSwitchParceiro()
        configuraListaTiposDePagamento()
        configuraListaHorasDiarias()
        listenerHoraEFormaDePagamento()

        tilDataDeInicio.setEndIconOnClickListener {
            mostrarSelecionadorDeDatas(etDataDeInicio)
        }

        tilDataPrevFinal.setEndIconOnClickListener {
            mostrarSelecionadorDeDatas(etDataPrevFinal)
        }

        var cliente: PessoaJuridica

        btCadastrarProposta = binding.cadastroPropostaBotaoCadastrarProposta

        actvClientes.setOnItemClickListener { _, _, position, _ ->
            cliente = listaClientes[position]
            Log.d("cliente", cliente.cnpj + " |\n " + cliente.toString())
        }

    }

    override fun onResume() {
        super.onResume()
        checkSwitchParceiro()
        configuraListaTiposDePagamento()
        configuraListaHorasDiarias()
        listenerHoraEFormaDePagamento()

    }

    private fun checkSwitchParceiro() {
        checkParceiro.setOnCheckedChangeListener { _, isChecked ->
            configuraViewsParceiro(isChecked)
        }
    }

    private fun configuraListasPessoaJuridica() {
        configiuraListaPorTipo(listaClientes, TipoContrato.CLIENTE)
        configiuraListaPorTipo(listaAssistentes, TipoContrato.ASSISTENTE)
        configiuraListaPorTipo(listaParceiros, TipoContrato.PARCEIRO)
    }

    private fun configuraViews() {
        actvClientes = binding.cadastroPropostaAutoCompleteCliente
        actvAssistentes = binding.cadastroPropostaAutoCompleteAssistente
        actvParceiros = binding.cadastroPropostaAutoCompleteParceiro
        actvFormaDePagamento = binding.cadastroPropostaAutoFormaPagamento
        actvHorasDiarias = binding.cadastroPropostaAutoHoraDiaria

        checkParceiro = binding.cadastroPropostaCheckboxParceiro

        tilParceiro = binding.cadastroPropostaTilParceiro
        tilValorParceiro = binding.cadastroPropostaTilValorParceiro
        tilDataDeInicio = binding.cadastroPropostaTilDataInicio
        tilDataPrevFinal = binding.cadastroPropostaTilDataPrevisaoTermino

        etValorCliente = binding.cadastroPropostaValorCliente
        etValorAssistente = binding.cadastroPropostaValorAssistente
        etValoParceiro = binding.cadastroPropostaValorParceiro
        etDataDeInicio = binding.cadastroPropostaAutoDataInicio
        etDataPrevFinal = binding.cadastroPropostaAutoDataPrevisaoTermino
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

    private fun configuraListaTiposDePagamento() {
        val arrayAdapter = ArrayAdapter(this, R.layout.list_item, listaFormasDePagamento)
        actvFormaDePagamento.setAdapter(arrayAdapter)
    }

    private fun configuraListaHorasDiarias() {
        val arrayAdapter = ArrayAdapter(this, R.layout.list_item, listaHorasDia)
        actvHorasDiarias.setAdapter(arrayAdapter)
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

    private fun listenerHoraEFormaDePagamento() {
        actvHorasDiarias.setOnItemClickListener { _, _, position, _ ->
            if (listaHorasDia[position] != 6) {
                listaFormasDePagamento.clear()
                listaFormasDePagamento.addAll(FormaDePagamento.entries)
                configuraListaTiposDePagamento()
                actvFormaDePagamento.setText("")
            } else {
                listaFormasDePagamento.clear()
                listaFormasDePagamento.add(FormaDePagamento.MENSAL)
                configuraListaTiposDePagamento()
                actvFormaDePagamento.setText("")
            }
        }
    }

    private  fun  mostrarSelecionadorDeDatas (editText: EditText) {
        val calendario = Calendar.getInstance()
        val selecionadorDeDataDialog = DatePickerDialog(
            this , { _, ano: Int, mes: Int, diaDoMes: Int ->
                calendario.set(ano, mes, diaDoMes)
                val formattedDate = ConversorDeDatas().converterCalendarParaString(calendario)
                editText.setText(formattedDate)
            },
            calendario.get(Calendar.YEAR),
            calendario.get(Calendar.MONTH),
            calendario.get(Calendar.DAY_OF_MONTH)
        )
        selecionadorDeDataDialog.show()
    }
}