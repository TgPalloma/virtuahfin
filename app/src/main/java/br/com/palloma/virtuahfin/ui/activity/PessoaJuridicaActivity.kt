package br.com.palloma.virtuahfin.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.palloma.virtuahfin.R
import br.com.palloma.virtuahfin.dao.PessoaJuridicaDao
import br.com.palloma.virtuahfin.databinding.ActivityPessoaJuridicaBinding
import br.com.palloma.virtuahfin.model.PessoaJuridica
import br.com.palloma.virtuahfin.util.ConversorDeDatas

class PessoaJuridicaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPessoaJuridicaBinding.inflate(layoutInflater)
    }

    private val pessoaJuridicaDao = PessoaJuridicaDao()
    private val conversorDeDatas = ConversorDeDatas()

    private lateinit var tvNomeFantasia: TextView
    private lateinit var tvRazaoSocial: TextView
    private lateinit var tvCnpj: TextView

    private lateinit var tvNomeResposavel: TextView
    private lateinit var tvTelefone: TextView
    private lateinit var tvEmail: TextView

    private lateinit var tvTipoContrato: TextView
    private lateinit var tvDataCadastro: TextView
    private lateinit var swStatus: SwitchCompat
    private lateinit var tvStatus: TextView

    private lateinit var btEditar : Button
    private lateinit var btExcluir : ImageButton

    private lateinit var pessoaJuridica: PessoaJuridica
    private var index: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        supportActionBar?.hide()

        index = verificaIntent()

        configuraViews()
        defineConteudoDasViews()
        configuraSwitchStatus()
        configuraBotaoEditar(index)
        configuraBotaoExcluir()
    }

    private fun verificaIntent(): Int {
        val index = intent.getIntExtra("indexPessoaJuridica", 1)
        Log.d("VemIndex", index.toString())
        pessoaJuridica = pessoaJuridicaDao.acessarLista()[index]
        return index
    }

    override fun onResume() {
        super.onResume()
        pessoaJuridica = pessoaJuridicaDao.acessarLista()[index]
        defineConteudoDasViews()
    }

    private fun configuraBotaoExcluir() {
        btExcluir.setOnClickListener {
            dialogAlertConfirmaExclusao()
        }
    }

    private fun dialogAlertConfirmaExclusao() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setMessage(
                "Você perderá todas as informações referentes e não há como ser desfeita."
            )
            .setTitle("Confirmar Exclusão")
            .setPositiveButton("Excluir") { dialog, which ->
                pessoaJuridicaDao.excluir(pessoaJuridica)
                finish()
            }.setNegativeButton("Cancelar") { dialog, which ->
                Toast.makeText(this, "Nada foi excluído", Toast.LENGTH_LONG).show()
            }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun configuraBotaoEditar(index: Int) {
        btEditar.setOnClickListener {
            val intent = Intent(this, CadastrarPessoaJuridicaActivity::class.java)
            intent.putExtra("indexPessoaJuridica", index)
            startActivity(intent)
        }
    }

    private fun configuraSwitchStatus() {
        swStatus.setOnCheckedChangeListener { _, isChecked ->
            pessoaJuridicaDao.ativaDesativaCadastro(pessoaJuridica, isChecked)
            tvStatus.text = pessoaJuridica.statusToString()
        }
    }

    private fun configuraViews() {
        tvNomeFantasia = binding.visualizarPessoaJuridicaNomeFantasia
        tvRazaoSocial = binding.visualizarPessoaJuridicaRazaoSocial
        tvCnpj = binding.visualizarPessoaJuridicaCnpj

        tvNomeResposavel = binding.visualizarPessoaJuridicaNomeResponsavel
        tvTelefone = binding.visualizarPessoaJuridicaTelefone
        tvEmail = binding.visualizarPessoaJuridicaEmail

        tvTipoContrato = binding.visualizarPessoaJuridicaTipoContrato
        tvDataCadastro = binding.visualizarPessoaJuridicaDataCadastro
        swStatus = binding.visualizarPessoaJuridicaSwitvhStatus
        tvStatus = binding.visualizarPessoaJuridicaStatus

        btEditar = binding.visualizarPessoaJuridicaBotaoEditar
        btExcluir = binding.visualizarPessoaJuridicaBotaoExcluir
    }

    private fun defineConteudoDasViews() {
        tvNomeFantasia.text = pessoaJuridica.nomeFantasia
        tvRazaoSocial.text = pessoaJuridica.razaoSocial
        tvCnpj.text = pessoaJuridica.cnpj

        tvNomeResposavel.text = pessoaJuridica.nomeResponsavel
        tvTelefone.text = pessoaJuridica.telefone.toString()
        tvEmail.text = pessoaJuridica.email

        tvTipoContrato.text = pessoaJuridica.tipo.toString()
        tvDataCadastro.text = conversorDeDatas.converterLocalDateParaString(pessoaJuridica.dataDeCadastro)

        swStatus.isChecked = pessoaJuridica.status
        tvStatus.text = pessoaJuridica.statusToString()
    }
}