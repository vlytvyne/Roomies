package vl.roomies.ui.log_in

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import vl.roomies.R
import vl.roomies.databinding.ActivityLogInBinding
import vl.roomies.ui.home.HomeActivity
import vl.roomies.ui.sign_up.SignUpActivity
import vl.roomies.ui.splash.SplashActivity
import vl.roomies.utils.hideKeyboard

class LogInActivity : AppCompatActivity() {

	private lateinit var binding: ActivityLogInBinding
	private lateinit var viewmodel: LogInVM

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.activity_log_in)
		binding.lifecycleOwner = this
		viewmodel = LogInVM.create(this)
		binding.viewmodel = viewmodel
		setupVMObserver()
	}

	private fun setupVMObserver() {
		viewmodel.startSignUpAction.observe(this, Observer {
			SignUpActivity.start(this)
		})
		viewmodel.logInAction.observe(this, Observer {
			SplashActivity.start(this)
		})
		viewmodel.hideKeyboardAction.observe(this, Observer {
			hideKeyboard(this)
		})
		viewmodel.snackError.observe(this, Observer {
			Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
		})
	}

	companion object {

		fun start(activity: AppCompatActivity) {
			val intent = Intent(activity, LogInActivity::class.java)
			activity.startActivity(intent)
		}
	}
}
