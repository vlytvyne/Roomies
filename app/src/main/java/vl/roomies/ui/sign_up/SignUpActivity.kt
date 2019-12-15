package vl.roomies.ui.sign_up

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import vl.roomies.R
import vl.roomies.databinding.ActivitySignUpBinding
import vl.roomies.ui.HomeActivity

class SignUpActivity : AppCompatActivity() {

	private lateinit var binding: ActivitySignUpBinding
	private lateinit var viewmodel: SignUpVM

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
		binding.lifecycleOwner = this
		viewmodel = SignUpVM.create(this)
		binding.viewmodel = viewmodel
		setupVMObserver()
	}

	private fun setupVMObserver() {
		viewmodel.logInAction.observe(this, Observer {
			HomeActivity.start(this)
		})
	}

	companion object {

		fun start(activity: AppCompatActivity) {
			val intent = Intent(activity, SignUpActivity::class.java)
			activity.startActivity(intent)
		}
	}
}
