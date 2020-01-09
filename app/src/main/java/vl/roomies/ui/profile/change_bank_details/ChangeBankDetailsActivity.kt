package vl.roomies.ui.profile.change_bank_details

import android.app.Activity
import android.content.ClipboardManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
			setResult(Activity.RESULT_OK)
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
			etTitle.setText(clipboard.primaryClip?.getItemAt(0)?.text.toString())
		}
		tilBankLink.setEndIconOnClickListener {
			etBankLink.setText(clipboard.primaryClip?.getItemAt(0)?.text.toString())
		}
	}

	companion object {

		fun startForResult(fragment: Fragment, requestCode: Int) {
			val intent = Intent(fragment.activity!!, ChangeBankDetailsActivity::class.java)
			fragment.startActivityForResult(intent, requestCode)
		}
	}
}
