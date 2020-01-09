package vl.roomies.ui.purchases.creation

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_purchase_creation.*
import vl.roomies.R

class PurchaseCreationActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_purchase_creation)
		setupToolbar()
	}

	private fun setupToolbar() {
		toolbar.title = getString(R.string.label_create_purchase)
		toolbar.setNavigationIcon(R.drawable.ic_up_navigation_arrow)
		toolbar.setNavigationOnClickListener { finish() }
		toolbar.inflateMenu(R.menu.create)
	}

	companion object {

		fun start(activity: Activity) {
			val intent = Intent(activity, PurchaseCreationActivity::class.java)
			activity.startActivity(intent)
		}
	}
}
