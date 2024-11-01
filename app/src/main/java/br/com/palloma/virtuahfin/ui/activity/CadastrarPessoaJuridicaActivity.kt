package br.com.palloma.virtuahfin.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.palloma.virtuahfin.R
import br.com.palloma.virtuahfin.dao.PessoaJuridicaDao
import br.com.palloma.virtuahfin.databinding.ActivityCadastrarPessoaJuridicaBinding
import br.com.palloma.virtuahfin.model.PessoaJuridica
import br.com.palloma.virtuahfin.model.TipoContrato
import br.com.palloma.virtuahfin.services.PessoaJuridicaService

class CadastrarPessoaJuridicaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastrarPessoaJuridicaBinding.inflate(layoutInflater)
    }

    private val pessoaJuridicaDao = PessoaJuridicaDao()
    private lateinit var pessoaJuridica: PessoaJuridica

    private val listaDeTiposDeContrato = TipoContrato.entries.toTypedArray()

    private lateinit var etNomeFantasia: EditText
    private lateinit var etRazaoSocial: EditText
    private lateinit var etCnpj: EditText
    private lateinit var etNomeDoRepsonsavel: EditText
    private lateinit var etTelefone: EditText
    private lateinit var etEmail: EditText
    private lateinit var actvListaTipo: AutoCompleteTextView
    private lateinit var btCadastrarPessoaJuridica: Button

    private var modoCadastro = true
    private var index: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()
        setContentView(binding.root)

        configuraViews()
        configuraAdapterListItemTiposDeContrato()
        verificaSeExisteEConfiguraCampos()
        configuraAcaoBotaoCadastrar()

    }

    private fun verificaSeExisteEConfiguraCampos() {
        if (intent.hasExtra("indexPessoaJuridica")) {
            index = intent.getIntExtra("indexPessoaJuridica", -1)
            modoCadastro = false
            Log.d("VemIndex", index.toString())
            pessoaJuridica = pessoaJuridicaDao.acessarLista()[index]

            etNomeFantasia.setText(pessoaJuridica.nomeFantasia)
            etRazaoSocial.setText(pessoaJuridica.razaoSocial)

            etCnpj.setText(pessoaJuridica.cnpj)
            etCnpj.isEnabled = false
            val tilCnpj = binding.cadastroJuridicoTilCnpj
            tilCnpj.helperText = "Não pode ser editado"

            etNomeDoRepsonsavel.setText(pessoaJuridica.nomeResponsavel)
            etTelefone.setText(pessoaJuridica.telefone.toString())
            etEmail.setText(pessoaJuridica.email)

            actvListaTipo.setText(pessoaJuridica.tipo.toString())
        }
    }

    private fun configuraAcaoBotaoCadastrar() {
        btCadastrarPessoaJuridica.setOnClickListener {
            if (modoCadastro) {
                pessoaJuridicaDao.salvar(criaPessoaJuridicaPeloFormulario())
                Log.d("ModoCadastro", "Está Em Modo Cadastro")
            } else {
                pessoaJuridicaDao.atualizar(criaPessoaJuridicaPeloFormulario(), index)
                Log.d("Modo Edição", index.toString() + pessoaJuridica)
            }
            finish()
        }
    }

    private fun verificaCamposCadastroPessoaJuridica() {
        if (etNomeFantasia.text.isEmpty()) {
            val tilNomeFantasia = binding.cadastroJuridicoTilNomeFantasia
            tilNomeFantasia.error = "Preencha o Nome Fantasia"
            return
        }
        criaPessoaJuridicaPeloFormulario()
    }

    private fun criaPessoaJuridicaPeloFormulario(): PessoaJuridica {

        val nomeFantasia = etNomeFantasia.text.toString()
        val razaoSocial = etRazaoSocial.text.toString()
        val cnpj = etCnpj.text.toString()

        val nomeDoResponsavel = etNomeDoRepsonsavel.text.toString()
        val telefone = etTelefone.text.toString().toLong()
        val email = etEmail.text.toString()

        val tipoContrato = TipoContrato.valueOf(actvListaTipo.text.toString())

        val pessoaJuridica = PessoaJuridica(
            nomeFantasia,
            razaoSocial,
            cnpj,
            nomeDoResponsavel,
            telefone,
            email,
            tipoContrato
        )
        return pessoaJuridica
    }

    fun alertaDeTela(mensagem: String) {
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
    }

    private fun configuraAdapterListItemTiposDeContrato() {
        val arrayAdapter = ArrayAdapter(this, R.layout.list_item, listaDeTiposDeContrato)
        actvListaTipo.setAdapter(arrayAdapter)
    }

    private fun configuraViews() {
        etNomeFantasia = binding.cadastroJuridicoNomeFantasia
        etRazaoSocial = binding.cadastroJuridicoRazaoSocial
        etCnpj = binding.cadastroJuridicoCnpj

        etNomeDoRepsonsavel = binding.cadastroJuridicoNomeResponsavel
        etTelefone = binding.cadastroJuridicoTelefone
        etEmail = binding.cadastroJuridicoEmail

        actvListaTipo = binding.cadastroJuridicoTipoContrato

        btCadastrarPessoaJuridica = binding.cadastroJuridicoBotaoCadastrar
    }
}
