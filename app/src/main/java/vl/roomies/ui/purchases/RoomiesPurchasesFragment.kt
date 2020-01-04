package vl.roomies.ui.purchases

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import vl.roomies.R
import vl.roomies.app.RoomiesApp.Companion.appContext

class RoomiesPurchasesFragment : Fragment() {

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_roomies_purchases, container, false)
	}

	companion object {

		fun newInstance() = RoomiesPurchasesFragment()

		val tabTitle = appContext.getString(R.string.tab_title_roomies)
	}
}
