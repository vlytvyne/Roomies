package vl.roomies.ui.purchases.your

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeAdapter
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnItemSwipeListener
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_your_purchases.*

import vl.roomies.R
import vl.roomies.app.RoomiesApp
import vl.roomies.data.models.Purchase
import vl.roomies.databinding.VhYourPurchaseBinding
import vl.roomies.ui.purchases.creation.PurchaseCreationActivity
import vl.roomies.utils.HideFabOnScrollListener
import vl.roomies.utils.MarginItemDecoration
import vl.roomies.utils.createUndoDeleteSnack
import vl.roomies.utils.swipeLeftOrientation

const val RC_CREATE_PURCHASE = 1

class YourPurchasesFragment : Fragment() {

	private lateinit var viewmodel: YourPurchasesVM

	private val adapter = PurchasesAdapter()

	private val snackUndoDelete: Snackbar by lazy {
		createUndoDeleteSnack(fabCreatePurchase, R.string.hint_purchase_is_deleted,
			{ viewmodel.refreshPurchases() },
			{ purchaseToDelete?.let { viewmodel.deletePurchase(it) } })
	}

	private var purchaseToDelete: Purchase? = null

	private val onItemSwipeListener = object : OnItemSwipeListener<Purchase> {
		override fun onItemSwiped(position: Int, direction: OnItemSwipeListener.SwipeDirection, item: Purchase): Boolean {
			snackUndoDelete.show()
			purchaseToDelete = item
			return false
		}
	}

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
		fabCreatePurchase.setOnClickListener { PurchaseCreationActivity.startForResultCreate(this, RC_CREATE_PURCHASE) }
	}

	private fun setupRecycler() {
		recyclerPurchases.layoutManager = LinearLayoutManager(context!!)
		recyclerPurchases.adapter = adapter

		recyclerPurchases.orientation = swipeLeftOrientation
		recyclerPurchases.addItemDecoration(MarginItemDecoration(8, 4))
		recyclerPurchases.swipeListener = onItemSwipeListener
		recyclerPurchases.scrollListener = HideFabOnScrollListener(fabCreatePurchase)
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (resultCode == RESULT_OK) {
			viewmodel.refreshPurchases()
		}
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
		val binding = DataBindingUtil.bind<VhYourPurchaseBinding>(viewHolder.itemView)
		binding!!.purchase = item
	}

}