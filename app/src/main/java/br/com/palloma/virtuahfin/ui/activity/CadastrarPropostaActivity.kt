package br.com.palloma.virtuahfin.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.palloma.virtuahfin.R
import br.com.palloma.virtuahfin.dao.PessoaJuridicaDao
import br.com.palloma.virtuahfin.dao.PropostaDao
import br.com.palloma.virtuahfin.databinding.ActivityCadastrarPropostaBinding
import br.com.palloma.virtuahfin.model.FormaDePagamento
import br.com.palloma.virtuahfin.model.PessoaJuridica
import br.com.palloma.virtuahfin.model.Proposta
import br.com.palloma.virtuahfin.model.TipoContrato
import br.com.palloma.virtuahfin.util.ConversorDeDatas
import com.google.android.material.textfield.TextInputLayout
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.Calendar

class CadastrarPropostaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastrarPropostaBinding.inflate(layoutInflater)
    }

//    private val minimoDiasProposta = 30

    private val pessoaJuridicaDao = PessoaJuridicaDao()

    private var cliente: PessoaJuridica? = null
    private var assistente: PessoaJuridica? = null
    private var parceiro: PessoaJuridica? = null

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

    private lateinit var tilCliente: TextInputLayout
    private lateinit var tilValorCliente: TextInputLayout
    private lateinit var tilAssistente: TextInputLayout
    private lateinit var tilValorAssistente: TextInputLayout
    private lateinit var tilDescricao: TextInputLayout
    private lateinit var tilHoraDiaria: TextInputLayout
    private lateinit var tilFormaDePagamento: TextInputLayout
    private lateinit var tilParceiro: TextInputLayout
    private lateinit var tilValorParceiro: TextInputLayout
    private lateinit var tilDataDeInicio: TextInputLayout
    private lateinit var tilDataPrevFinal: TextInputLayout

    private lateinit var etValorCliente: EditText
    private lateinit var etValorAssistente: EditText
    private lateinit var etValorParceiro: EditText
    private lateinit var etDescricao: EditText
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
        
        clickListenerDatas(tilDataDeInicio, etDataDeInicio)
        clickListenerDatas(tilDataPrevFinal, etDataPrevFinal)

        listenerSelecionaCliente()
        listenerSelecionaAssistente()
        listenerSelecionaParceiro()

        listenerRemoveError(tilDataDeInicio)
        listenerRemoveError(tilDataPrevFinal)

        btCadastrarProposta.setOnClickListener {

            tilCliente.error = null
            tilValorCliente.error = null
            tilAssistente.error = null
            tilValorAssistente.error = null
            tilDescricao.error = null
            tilHoraDiaria.error = null

            val valorCliente = etValorCliente.text.toString()
            val valorAssistente = etValorAssistente.text.toString()
            val valorParceiro = etValorParceiro.text.toString()
            val descricao = etDescricao.text.toString()
            val dataInicio = etDataDeInicio.text.toString()
            val dataPrevFinal = etDataPrevFinal.text.toString()

            val horaDiaria = actvHorasDiarias.text.toString()
            val formaPagamento = actvFormaDePagamento.text.toString()

            if (cliente == null) {
                tilCliente.error = "Selecione um cliente"

            } else if (valorCliente.isEmpty()) {
                tilValorCliente.error = "Defina o valor a ser pago pelo cliente"

            } else if (assistente == null) {
                tilAssistente.error = "Selecione uma assistente para a proposta"

            } else if (valorAssistente.isEmpty().or(valorAssistente.toFloat() > valorCliente.toFloat())) {
                if (valorAssistente.isEmpty()) tilValorAssistente.error = "Defina o pagamento para a assistente"
                if ((valorAssistente.toFloat() > valorCliente.toFloat())) tilValorAssistente.error = "Assistente não pode ganhar mais que o cliente paga" else TODO()

            } else if (descricao.isEmpty()) {
                tilDescricao.error = "Descreva o serviço a ser realizado"

            } else if (horaDiaria == null) {
                tilHoraDiaria.error = "Defina de quantas horas será a proposta"

            } else if (formaPagamento.isEmpty()) {
                tilFormaDePagamento.error = "Defina a forma de pagamento"

            } else if (dataInicio.isEmpty().or(
                    ConversorDeDatas().converterStringParaLocalDate(dataInicio).isBefore(LocalDate.now()))) {

                if (dataInicio.isEmpty()) tilDataDeInicio.error = "Selecione uma data para inicio da proposta"
                if (ConversorDeDatas().converterStringParaLocalDate(dataInicio).isBefore(LocalDate.now()))
                    tilDataDeInicio.error = "Prposta não pode ser iniciada retroativamente" else TODO()
//
//            } else if ((dataPrevFinal != null).and(calculaPeriodoMinimo(dataInicio, dataPrevFinal))) {
//                tilDataPrevFinal.error = "Período mínimo de 30 dias"
//
            } else {
                val proposta = Proposta(
                    cliente!!,
                    assistente!!,
                    valorCliente.toFloat(),
                    valorAssistente.toFloat(),
                    descricao,
                    ConversorDeDatas().converterStringParaLocalDate(dataInicio),
                    horaDiaria.toInt(),
                    FormaDePagamento.valueOf(formaPagamento)
                )

                if (dataPrevFinal.isEmpty()) {
                } else proposta.dataFinalPrevista = ConversorDeDatas().converterStringParaLocalDate(dataPrevFinal)

                PropostaDao().salvarProposta(proposta)
                finish()
            }
        }
    }

    private fun calculaPeriodoMinimo(dataInicio: String, dataPrevFinal: String): Boolean {

        val lcDataInicio: LocalDate = ConversorDeDatas().converterStringParaLocalDate(dataInicio)
        val lcDataPrevFinal: LocalDate = ConversorDeDatas().converterStringParaLocalDate(dataPrevFinal)
        val dias = lcDataInicio.until(lcDataPrevFinal, ChronoUnit.DAYS).toString().toInt()
        Log.d("PeriodoMinimo", dias.toString())

        return dias < 29
    }

    private fun alertaDeTela(mensagem: String) {
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
    }

    private fun listenerSelecionaParceiro() {
        actvParceiros.setOnItemClickListener { _, _, position, _ ->
            parceiro = listaParceiros[position]
            Log.d("parceiroListener", parceiro.toString())
        }
    }

    private fun listenerSelecionaAssistente() {
        tilAssistente.error = null
        actvAssistentes.setOnItemClickListener { _, _, position, _ ->
            assistente = listaAssistentes[position]
            Log.d("assistenteListener", assistente.toString())
        }
    }

    private fun listenerSelecionaCliente() {
        tilCliente.error = null
        actvClientes.setOnItemClickListener { _, _, position, _ ->
            cliente = listaClientes[position]
            Log.d("clienteListener", cliente.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        checkSwitchParceiro()
        configuraListaTiposDePagamento()
        configuraListaHorasDiarias()
        listenerHoraEFormaDePagamento()

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

        tilCliente = binding.cadastroPropostaTilCliente
        tilValorCliente = binding.cadastroPropostaTilValorCliente
        tilAssistente = binding.cadastroPropostaTilAssistente
        tilValorAssistente = binding.cadastroPropostaTilValorAssistente
        tilDescricao = binding.cadastroPropostaTilDescricao
        tilHoraDiaria = binding.cadastroPropostaTilHoraDiaria
        tilFormaDePagamento = binding.cadastroPropostaTilFormaPagamento
        tilParceiro = binding.cadastroPropostaTilParceiro
        tilValorParceiro = binding.cadastroPropostaTilValorParceiro
        tilDataDeInicio = binding.cadastroPropostaTilDataInicio
        tilDataPrevFinal = binding.cadastroPropostaTilDataPrevisaoTermino

        etValorCliente = binding.cadastroPropostaValorCliente
        etValorAssistente = binding.cadastroPropostaValorAssistente
        etValorParceiro = binding.cadastroPropostaValorParceiro
        etDescricao = binding.cadastroPropostaDescricao
        etDataDeInicio = binding.cadastroPropostaAutoDataInicio
        etDataPrevFinal = binding.cadastroPropostaAutoDataPrevisaoTermino

        btCadastrarProposta = binding.cadastroPropostaBotaoCadastrarProposta
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

    private fun checkSwitchParceiro() {
        checkParceiro.setOnCheckedChangeListener { _, isChecked ->
            configuraViewsParceiro(isChecked)
        }
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

    private fun listenerRemoveError (textInputLayout: TextInputLayout) {
        textInputLayout.setErrorIconOnClickListener {
            textInputLayout.error = null
        }
    }

    private fun clickListenerDatas (
        textInputLayout: TextInputLayout,
        editText: EditText) {
        textInputLayout.setEndIconOnClickListener {
            mostrarSelecionadorDeDatas(editText)
        }
    }
}