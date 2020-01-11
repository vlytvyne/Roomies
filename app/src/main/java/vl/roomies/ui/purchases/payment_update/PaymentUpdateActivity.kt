package vl.roomies.ui.purchases.payment_update

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_payment_update.*
import timber.log.Timber
import vl.roomies.R
import vl.roomies.data.models.Purchase

const val PURCHASE_KEY = "purchaseKey"

class PaymentUpdateActivity : AppCompatActivity() {

	private lateinit var viewmodel: PaymentUpdateVM

	private val adapter = PaymentUpdateContributorAdapter()

	private val purchase: Purchase by lazy {
		intent.getParcelableExtra(PURCHASE_KEY) as Purchase
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_payment_update)
		viewmodel = PaymentUpdateVM.create(this)

		setupVMObservers()
		setupToolbar()
		setupRecycler()

		btnEverybodyPaid.setOnClickListener { adapter.everybodyPaid() }
	}

	private fun setupVMObservers() {
		viewmodel.isLoading.observe(this, Observer {
			progressBar.isVisible = it
		})
		viewmodel.closeWindow.observe(this, Observer {
			setResult(Activity.RESULT_OK)
			finish()
		})
		viewmodel.snackError.observe(this, Observer {
			Snackbar.make(toolbar, it, Snackbar.LENGTH_LONG).show()
		})
	}

	private fun setupToolbar() {
		toolbar.title = purchase.title
		toolbar.setNavigationIcon(R.drawable.ic_up_navigation_arrow)
		toolbar.setNavigationOnClickListener { finish() }
		toolbar.inflateMenu(R.menu.save)
		toolbar.menu.findItem(R.id.toolbar_save).setOnMenuItemClickListener { viewmodel.onSaveClick(purchase); true }
	}

	private fun setupRecycler() {
		recyclerContributors.layoutManager = LinearLayoutManager(this)
		recyclerContributors.isNestedScrollingEnabled = false
		recyclerContributors.adapter = adapter
		adapter.setContributors(purchase.contributors)
	}

	companion object {

		fun startForResult(fragment: Fragment, purchase: Purchase, requestCode: Int) {
			val intent = Intent(fragment.activity, PaymentUpdateActivity::class.java)
			intent.putExtra(PURCHASE_KEY, purchase)
			fragment.startActivityForResult(intent, requestCode)
		}
	}
}
