package br.com.palloma.virtuahfin.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.palloma.virtuahfin.R
import br.com.palloma.virtuahfin.dao.PessoaJuridicaDao
import br.com.palloma.virtuahfin.databinding.ActivityListaCadastroJuridicoBinding
import br.com.palloma.virtuahfin.ui.adapter.ListaPessoaJuridicaAdapter

class ListaCadastroJuridicoActivity : AppCompatActivity() {

    private val adapter = ListaPessoaJuridicaAdapter(this, PessoaJuridicaDao().acessarLista())

    private val binding by lazy {
        ActivityListaCadastroJuridicoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.listaPessoaJuridicaToolbar)
        configuraAdapterDaRecyclerView()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_cadastrar -> {
            startActivity(Intent(this, CadastrarPessoaJuridicaActivity::class.java))
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

    private fun configuraAdapterDaRecyclerView() {
        val rvListaPessoaJuridica = binding.listaPessoaJuridicaRecyclerview
        rvListaPessoaJuridica.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        adapter.atualiza(PessoaJuridicaDao().acessarLista())
        Log.d("PassouAqui", "onResume: Passou Aqui")
    }
}