package br.com.palloma.virtuahfin.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.palloma.virtuahfin.R
import br.com.palloma.virtuahfin.dao.PropostaDao
import br.com.palloma.virtuahfin.databinding.ActivityListaPropostaBinding
import br.com.palloma.virtuahfin.ui.adapter.ListaPropostaAdapter

class ListaPropostaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityListaPropostaBinding.inflate(layoutInflater)
    }

    private val propostaDao = PropostaDao()

    private val listAdapter = ListaPropostaAdapter(propostaDao.acessaLista(), this)

    private lateinit var rvListaPropostas: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.listaPropostaToolbar)
        rvListaPropostas = binding.listaPropostaRecyclerview
        rvListaPropostas.adapter = listAdapter

    }

    override fun onResume() {
        super.onResume()
        listAdapter.atualiza(propostaDao.acessaLista())
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_cadastrar -> {
            startActivity(Intent(this, CadastrarPropostaActivity::class.java))
            true
        }
        R.id.menu_buscar -> {
            Toast.makeText(this, "Ainda não implementado", Toast.LENGTH_LONG).show()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
}