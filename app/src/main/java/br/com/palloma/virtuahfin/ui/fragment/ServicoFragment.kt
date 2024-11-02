package br.com.palloma.virtuahfin.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.palloma.virtuahfin.R
import br.com.palloma.virtuahfin.databinding.ServicoFragmentBinding

class ServicoFragment(
//    val binding: ServicoFragmentBinding
): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.servico_fragment, container, false)
    }
}