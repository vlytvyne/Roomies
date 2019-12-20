package vl.roomies.ui.profile.change_bank_details

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_change_bank_details.*
import vl.roomies.R
import vl.roomies.databinding.ActivityChangeBankDetailsBinding

class ChangeBankDetailsActivity : AppCompatActivity() {

	private lateinit var binding: ActivityChangeBankDetailsBinding
	private lateinit var viewmodel: ChangeBankDetailsVM

	private val clipboard: ClipboardManager by lazy { getSystemService(CLIPBOARD_SERVICE) as ClipboardManager }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.activity_change_bank_details)
		binding.lifecycleOwner = this
		viewmodel = ChangeBankDetailsVM.create(this)
		binding.viewmodel = viewmodel

		setupVMObservers()
		setupToolbar()
		setupInput()
	}

	private fun setupVMObservers() {
		viewmodel.closeWindow.observe(this, Observer {
			finish()
		})
	}

	private fun setupToolbar() {
		toolbar.title = getString(R.string.label_change_name)
		toolbar.setNavigationIcon(R.drawable.ic_up_navigation_arrow)
		toolbar.setNavigationOnClickListener { finish() }
		toolbar.inflateMenu(R.menu.save)
		toolbar.menu.findItem(R.id.toolbar_save).setOnMenuItemClickListener {
			viewmodel.onSaveClick()
			true
		}
	}

	private fun setupInput() {
		tilCardNumber.setEndIconOnClickListener {
			etCardNumber.setText(clipboard.primaryClip?.getItemAt(0)?.text.toString())
		}
		tilBankLink.setEndIconOnClickListener {
			etBankLink.setText(clipboard.primaryClip?.getItemAt(0)?.text.toString())
		}
	}

	companion object {

		fun start(activity: Activity) {
			val intent = Intent(activity, ChangeBankDetailsActivity::class.java)
			activity.startActivity(intent)
		}
	}
}
