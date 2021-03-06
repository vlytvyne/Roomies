package vl.roomies.ui.purchases

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.fragment_purchases.*

import vl.roomies.R
import vl.roomies.ui.purchases.your_contributions.YourContributionsFragment
import vl.roomies.ui.purchases.your_purchases.YourPurchasesFragment

class PurchasesFragment : Fragment() {

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_purchases, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupToolbar()

		viewPager.adapter = PurchasePagerAdapter(activity!!.supportFragmentManager)
		tabs.setupWithViewPager(viewPager)
	}

	private fun setupToolbar() {
		toolbar.title = getString(R.string.label_purchases)
	}

	companion object {

		fun newInstance() = PurchasesFragment()
	}
}

private class PurchasePagerAdapter(supportFragmentManager: FragmentManager) : FragmentStatePagerAdapter(supportFragmentManager) {

	override fun getItem(position: Int) =
		when(position) {
			0 -> YourPurchasesFragment.newInstance()
			else -> YourContributionsFragment.newInstance()
		}

	override fun getPageTitle(position: Int) =
		when(position) {
			0 -> YourPurchasesFragment.tabTitle
			else -> YourContributionsFragment.tabTitle
		}

	override fun getCount() = 2

}