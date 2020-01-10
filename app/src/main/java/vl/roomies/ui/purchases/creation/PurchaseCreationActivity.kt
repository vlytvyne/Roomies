package vl.roomies.ui.purchases.creation

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_purchase_creation.*
import vl.roomies.R
import vl.roomies.databinding.ActivityPurchaseCreationBinding
import vl.roomies.ui.fridge.creation.ContributorAdapter

class PurchaseCreationActivity : AppCompatActivity() {

	private lateinit var viewmodel: PurchaseCreationVM
	private lateinit var binding: ActivityPurchaseCreationBinding
	private val adapter = ContributorAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.activity_purchase_creation)
		binding.lifecycleOwner = this
		viewmodel = PurchaseCreationVM.create(this)
		binding.viewmodel = viewmodel
		setupVMObservers()
		setupToolbar()
		setupRecycler()
		setupButtons()
	}

	private fun setupVMObservers() {
		viewmodel.setUsersToContributorsAction.observe(this, Observer {
			adapter.setUsers(it)
		})
	}

	private fun setupToolbar() {
		toolbar.title = getString(R.string.label_create_purchase)
		toolbar.setNavigationIcon(R.drawable.ic_up_navigation_arrow)
		toolbar.setNavigationOnClickListener { finish() }
		toolbar.inflateMenu(R.menu.create)
	}

	private fun setupRecycler() {
		recyclerContributors.layoutManager = LinearLayoutManager(this)
		recyclerContributors.isNestedScrollingEnabled = false
		recyclerContributors.adapter = adapter
	}

	private fun setupButtons() {
		btnAddAllToContributors.setOnClickListener { adapter.addAllToContributors() }
		btnRemoveAllFromContributors.setOnClickListener { adapter.removeAllFromContributors() }
		switchPayEqually.setOnCheckedChangeListener { switch, checked ->
			if (checked) {
				btnAddAllToContributors.isEnabled = false
				btnRemoveAllFromContributors.isEnabled = false
				adapter.onPayEqually(viewmodel.purchaseCurrentCost)
			} else {
				btnAddAllToContributors.isEnabled = true
				btnRemoveAllFromContributors.isEnabled = true
				adapter.offPayEqually()
			}
		}
	}

	companion object {

		fun start(activity: Activity) {
			val intent = Intent(activity, PurchaseCreationActivity::class.java)
			activity.startActivity(intent)
		}
	}
}