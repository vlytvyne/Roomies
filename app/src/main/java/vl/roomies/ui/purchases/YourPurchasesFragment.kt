package vl.roomies.ui.purchases

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import vl.roomies.R
import vl.roomies.app.RoomiesApp

class YourPurchasesFragment : Fragment() {

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_your_purchases, container, false)
	}

	companion object {

		fun newInstance() = YourPurchasesFragment()

		val tabTitle = RoomiesApp.appContext.getString(R.string.tab_title_yours)
	}
}
