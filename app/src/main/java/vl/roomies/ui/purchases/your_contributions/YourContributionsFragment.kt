package vl.roomies.ui.purchases.your_contributions

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.databinding.BindableItem
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_your_contributions.*

import vl.roomies.R
import vl.roomies.app.RoomiesApp
import vl.roomies.data.models.Purchase
import vl.roomies.databinding.VhYourContributionBinding
import vl.roomies.ui.purchases.payment.PaymentActivity
import vl.roomies.utils.MarginItemDecoration

class YourContributionsFragment : Fragment() {

	private lateinit var viewmodel: YourContributionsVM

	private val adapter = GroupAdapter<GroupieViewHolder>()

	override fun onAttach(context: Context) {
		super.onAttach(context)
		viewmodel = YourContributionsVM.create(this)
		setupVMObservers()
	}

	private fun setupVMObservers() {
		viewmodel.setPurchasesAction.observe(this, Observer {
			adapter.clear()
			val items = it.map { purchase -> PurchaseItem(purchase) }
			items.forEach { it.onPayClick = { purchase ->  PaymentActivity.start(activity!!, purchase) } }
			adapter.addAll(items)
		})
		viewmodel.isLoading.observe(this, Observer {
			refreshLayout.isRefreshing = it
		})
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_your_contributions, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupRecycler()
		setupRefresh()
	}

	private fun setupRecycler() {
		recyclerPurchases.layoutManager = LinearLayoutManager(context)
		recyclerPurchases.addItemDecoration(MarginItemDecoration(8, 4))
		recyclerPurchases.adapter = adapter
	}

	private fun setupRefresh() {
		refreshLayout.setOnRefreshListener { viewmodel.refreshPurchases() }
		refreshLayout.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.indigo_500))
	}

	companion object {

		fun newInstance() =
			YourContributionsFragment()

		val tabTitle = RoomiesApp.appContext.getString(R.string.tab_title_your_contributions)
	}
}

class PurchaseItem(val purchase: Purchase): BindableItem<VhYourContributionBinding>() {

	var onPayClick: ((Purchase) -> Unit)? = null

	override fun getLayout() = R.layout.vh_your_contribution

	override fun bind(viewBinding: VhYourContributionBinding, position: Int) {
		viewBinding.purchase = purchase
		viewBinding.payButton.setOnClickListener { onPayClick?.invoke(purchase) }
	}

}
