package vl.roomies.ui.profile

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_profile.*

import vl.roomies.R
import vl.roomies.data.constants.NEW_NAME_KEY
import vl.roomies.databinding.FragmentProfileBinding
import vl.roomies.ui.profile.change_name.ChangeNameActivity

const val RC_CHANGE_NAME = 1

class ProfileFragment : Fragment() {

	private lateinit var binding: FragmentProfileBinding
	private lateinit var viewmodel: ProfileVM

	private val logOutConfirmation: MaterialAlertDialogBuilder by lazy {
		MaterialAlertDialogBuilder(context)
			.setTitle("Are you sure you want to log out?")
			.setPositiveButton("Log out") { _, _ ->
				viewmodel.logOut()
			}
			.setNegativeButton("Cancel") { _, _ -> }
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		viewmodel = ProfileVM.create()
		setupVMObservers()
	}

	private fun setupVMObservers() {
		viewmodel.closeWindow.observe(this, Observer {
			activity!!.finishAffinity()
		})
		viewmodel.changeNameAction.observe(this, Observer {
			ChangeNameActivity.startForResult(this, RC_CHANGE_NAME)
		})
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
		binding.lifecycleOwner = this
		binding.viewmodel = viewmodel
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		setupToolbar()
	}

	private fun setupToolbar() {
		toolbar.title = getString(R.string.label_profile)
		toolbar.inflateMenu(R.menu.log_out)
		toolbar.menu.findItem(R.id.toolbar_log_out).setOnMenuItemClickListener {
			logOutConfirmation.show()
			true
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		if (resultCode == RESULT_OK) {
			when (requestCode) {
				RC_CHANGE_NAME -> Snackbar.make(binding.root, data!!.getStringExtra(NEW_NAME_KEY), Snackbar.LENGTH_LONG).show()
			}
		}
	}

	companion object {

		fun newInstance() = ProfileFragment()
	}
}
