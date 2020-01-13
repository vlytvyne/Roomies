package vl.roomies.ui.purchases.payment

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_payment.*
import vl.roomies.R
import vl.roomies.data.models.Purchase
import vl.roomies.databinding.ActivityPaymentBinding


const val PURCHASE_KEY = "purchaseKey"

class PaymentActivity : AppCompatActivity() {

	private lateinit var binding: ActivityPaymentBinding

	private val clipboard: ClipboardManager by lazy { getSystemService(CLIPBOARD_SERVICE) as ClipboardManager }

	private val purchase: Purchase by lazy {
		intent.getParcelableExtra(PURCHASE_KEY) as Purchase
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
		binding.lifecycleOwner = this
		binding.purchase = purchase
		setupToolbar()
		setupButtons()
	}

	private fun setupToolbar() {
		toolbar.title = purchase.title
		toolbar.setNavigationIcon(R.drawable.ic_up_navigation_arrow)
		toolbar.setNavigationOnClickListener { finish() }
		toolbar.inflateMenu(R.menu.done)
		toolbar.menu.findItem(R.id.toolbar_done).setOnMenuItemClickListener { finish(); true }
	}

	private fun setupButtons() {
		btnCopyCardNumber.setOnClickListener {
			val clip = ClipData.newPlainText("clip", textCardNumber.text)
			clipboard.setPrimaryClip(clip)
			Snackbar.make(toolbar, R.string.hint_card_number_is_copied, Snackbar.LENGTH_LONG).show()
		}
	}

	companion object {

		fun start(activity: Activity, purchase: Purchase) {
			val intent = Intent(activity, PaymentActivity::class.java)
			intent.putExtra(PURCHASE_KEY, purchase)
			activity.startActivity(intent)
		}
	}
}
