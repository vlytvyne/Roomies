package vl.roomies.ui.profile.change_name

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_change_name.*
import kotlinx.android.synthetic.main.fragment_profile.toolbar
import vl.roomies.R
import vl.roomies.databinding.ActivityChangeNameBinding

class ChangeNameActivity : AppCompatActivity() {

	private lateinit var binding: ActivityChangeNameBinding
	private lateinit var viewmodel: ChangeNameVM

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.activity_change_name)
		binding.lifecycleOwner = this
		viewmodel = ChangeNameVM.create(this)
		binding.viewmodel = viewmodel

		setupVMObservers()
		setupToolbar()
		etCardNumber.requestFocus()
	}

	private fun setupVMObservers() {
		viewmodel.closeWindow.observe(this, Observer {
			setResult(Activity.RESULT_OK)
			finish()
		})
		viewmodel.snackError.observe(this, Observer {
			Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
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

	companion object {

		fun startForResult(fragment: Fragment, requestCode: Int) {
			val intent = Intent(fragment.activity!!, ChangeNameActivity::class.java)
			fragment.startActivityForResult(intent, requestCode)
		}
	}
}
