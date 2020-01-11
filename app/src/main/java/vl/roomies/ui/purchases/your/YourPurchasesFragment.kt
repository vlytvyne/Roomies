package vl.roomies.ui.purchases.your

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeAdapter
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import kotlinx.android.synthetic.main.fragment_your_purchases.*
import kotlinx.android.synthetic.main.vh_your_purchase.view.*

import vl.roomies.R
import vl.roomies.app.RoomiesApp
import vl.roomies.data.models.Purchase
import vl.roomies.data.source.currencyChar
import vl.roomies.utils.MarginItemDecoration

class YourPurchasesFragment : Fragment() {

	private lateinit var viewmodel: YourPurchasesVM

	private val adapter = PurchasesAdapter()

	override fun onAttach(context: Context) {
		super.onAttach(context)
		viewmodel = YourPurchasesVM.create(this)
		setupVMObservers()
	}

	private fun setupVMObservers() {
		viewmodel.setPurchasesAction.observe(this, Observer {
			adapter.dataSet = it
		})
		viewmodel.isLoading.observe(this, Observer {
			progressBar.isVisible = it
		})
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_your_purchases, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupRecycler()
	}

	private fun setupRecycler() {
		recyclerPurchases.layoutManager = LinearLayoutManager(context!!)
		recyclerPurchases.adapter = adapter
		recyclerPurchases.orientation!!.removeSwipeDirectionFlag(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.RIGHT)
		recyclerPurchases.addItemDecoration(MarginItemDecoration(8, 4))
	}

	companion object {

		fun newInstance() = YourPurchasesFragment()

		val tabTitle = RoomiesApp.appContext.getString(R.string.tab_title_yours)
	}
}

private class PurchasesAdapter: DragDropSwipeAdapter<Purchase, PurchasesAdapter.VH>() {

	class VH(itemView: View) : DragDropSwipeAdapter.ViewHolder(itemView)

	override fun getViewHolder(itemView: View) =
		VH(itemView)

	override fun getViewToTouchToStartDraggingItem(item: Purchase, viewHolder: VH, position: Int) = null

	override fun onBindViewHolder(item: Purchase, viewHolder: VH, position: Int) {
		viewHolder.itemView.textTitle.text = item.title
		viewHolder.itemView.textDescription.text = if (item.description.isBlank()) "No description" else item.description
		viewHolder.itemView.textCost.text = item.cost + currencyChar
		viewHolder.itemView.textContributors.text = item.contributors.size.toString() + " contributors"
	}

}