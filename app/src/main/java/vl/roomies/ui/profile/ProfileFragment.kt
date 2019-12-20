package vl.roomies.ui.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_profile.*

import vl.roomies.R
import vl.roomies.databinding.FragmentProfileBinding
import vl.roomies.ui.profile.change_name.ChangeNameActivity

class ProfileFragment : Fragment() {

	private lateinit var binding: FragmentProfileBinding
	private lateinit var viewmodel: ProfileVM

	private val logOutConfirmation: MaterialAlertDialogBuilder by lazy {
		MaterialAlertDialogBuilder(context)
			.setTitle(R.string.confirmation_log_out)
			.setPositiveButton(R.string.btn_log_out) { _, _ -> viewmodel.logOut() }
			.setNegativeButton(R.string.btn_cancel) { _, _ -> }
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		viewmodel = ProfileVM.create(this)
		setupVMObservers()
	}

	private fun setupVMObservers() {
		viewmodel.closeWindow.observe(this, Observer {
			activity!!.finishAffinity()
		})
		viewmodel.changeNameAction.observe(this, Observer {
			ChangeNameActivity.start(activity!!)
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

	companion object {

		fun newInstance() = ProfileFragment()
	}
}
