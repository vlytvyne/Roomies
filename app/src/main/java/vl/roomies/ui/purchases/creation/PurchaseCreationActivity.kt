package vl.roomies.ui.purchases.creation

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_purchase_creation.*
import vl.roomies.R
import vl.roomies.databinding.ActivityPurchaseCreationBinding
import vl.roomies.ui.fridge.creation.ContributorAdapter
import vl.roomies.utils.CutDigitsAfterDotWatcher

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
		setupInput()
	}

	private fun setupVMObservers() {
		viewmodel.setUsersToContributorsAction.observe(this, Observer {
			adapter.setUsers(it)
		})
		viewmodel.snackError.observe(this, Observer {
			Snackbar.make(toolbar, it, Snackbar.LENGTH_LONG).show()
		})
		viewmodel.closeWindow.observe(this, Observer {
			setResult(Activity.RESULT_OK)
			finish()
		})
	}

	private fun setupToolbar() {
		toolbar.title = getString(R.string.label_create_purchase)
		toolbar.setNavigationIcon(R.drawable.ic_up_navigation_arrow)
		toolbar.setNavigationOnClickListener { finish() }
		toolbar.inflateMenu(R.menu.create)
		toolbar.menu.findItem(R.id.toolbar_create).setOnMenuItemClickListener { viewmodel.onCreateClick(adapter.chippedInContributors); true }
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
			etCost.isEnabled = !checked
			btnAddAllToContributors.isEnabled = !checked
			btnRemoveAllFromContributors.isEnabled = !checked
			if (checked) {
				adapter.onPayEqually(viewmodel.purchaseCostDouble)
			} else {
				adapter.offPayEqually()
			}
		}
	}

	private fun setupInput() {
		etCost.addTextChangedListener(CutDigitsAfterDotWatcher(2))
	}

	companion object {

		fun startForResultCreate(fragment: Fragment, requestCode: Int) {
			val intent = Intent(fragment.activity, PurchaseCreationActivity::class.java)
			fragment.startActivityForResult(intent, requestCode)
		}
	}
}