package br.com.palloma.virtuahfin.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.palloma.virtuahfin.R
import br.com.palloma.virtuahfin.dao.PessoaJuridicaDao
import br.com.palloma.virtuahfin.data.PessoaJuridicaExp
import br.com.palloma.virtuahfin.databinding.ActivityDashBoardBinding
import br.com.palloma.virtuahfin.model.PessoaJuridica
import br.com.palloma.virtuahfin.model.TipoContrato

class DashBoardActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDashBoardBinding.inflate(layoutInflater)
    }

    lateinit var btCadastroJuridico: Button
    lateinit var btGestaoDeProposta: Button
    lateinit var btRegistroDiario: Button
    lateinit var btAreaDeFinancas: Button


    private val pessoaJuridicaExp = PessoaJuridicaExp()
    private val pessoaJuridicaDao = PessoaJuridicaDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dash_board)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (pessoaJuridicaDao.acessarLista().isEmpty()) {
            pessoaJuridicaExp.dadosDeExperiencia()
        }

        supportActionBar?.hide()
        setContentView(binding.root)

        btCadastroJuridico = binding.dashboardBtCadastroJuridico
        btGestaoDeProposta = binding.dashboardBtGestaoPropostas
        btRegistroDiario = binding.dashboardBtRegistroDiario
        btAreaDeFinancas = binding.dashboardBtAreaFinancas

        val avisoNaoImplementado = Toast.makeText(this, "Não Implementado Ainda", Toast.LENGTH_SHORT)

        btCadastroJuridico.setOnClickListener {
            startActivity(Intent(this, ListaCadastroJuridicoActivity::class.java))
        }

        btGestaoDeProposta.setOnClickListener {
            startActivity(Intent(this, CadastrarPropostaActivity::class.java))
        }

        btRegistroDiario.setOnClickListener {
            avisoNaoImplementado.show()
        }

        btAreaDeFinancas.setOnClickListener {
            avisoNaoImplementado.show()
        }
    }
}