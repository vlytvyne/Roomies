package vl.roomies.ui.log_in

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import vl.roomies.R
import vl.roomies.app.RoomiesApp.Companion.firebaseAuth
import vl.roomies.databinding.ActivityLogInBinding
import vl.roomies.ui.HomeActivity
import vl.roomies.ui.sign_up.SignUpActivity

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

		if (firebaseAuth.currentUser != null) {
			Log.d("TAGG", firebaseAuth.currentUser!!.displayName)
		}
	}

	private fun setupVMObserver() {
		viewmodel.startSignUp.observe(this, Observer {
			SignUpActivity.start(this)
		})
		viewmodel.logInAction.observe(this, Observer {
			HomeActivity.start(this)
		})
	}

	companion object {

		fun start(activity: AppCompatActivity) {
			val intent = Intent(activity, LogInActivity::class.java)
			activity.startActivity(intent)
		}
	}
}
