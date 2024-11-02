package br.com.palloma.virtuahfin.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.com.palloma.virtuahfin.ui.fragment.PeriodoFragment
import br.com.palloma.virtuahfin.ui.fragment.ServicoFragment

class TabPropostaAdapter(
    fragmentActivity: FragmentActivity
): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return  when (position) {
            0 -> ServicoFragment()
            1 -> PeriodoFragment()
            else -> ServicoFragment()
        }
    }
}