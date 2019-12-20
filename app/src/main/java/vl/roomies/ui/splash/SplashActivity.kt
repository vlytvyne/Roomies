package vl.roomies.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import vl.roomies.R
import vl.roomies.data.models.User
import vl.roomies.data.source.FirebaseRepository
import vl.roomies.data.source.currentUser
import vl.roomies.ui.home.HomeActivity
import vl.roomies.ui.log_in.LogInActivity

class SplashActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_splash)

		if (FirebaseRepository.isUserLoggedIn()) {
			FirebaseRepository.getCurrentUserInfo()
				.addOnSuccessListener {
					currentUser = it.toObject(User::class.java) as User
					HomeActivity.start(this)
				}
		} else {
			LogInActivity.start(this)
		}
	}

	companion object {

		fun start(activity: AppCompatActivity) {
			val intent = Intent(activity, SplashActivity::class.java)
			activity.startActivity(intent)
		}
	}
}
