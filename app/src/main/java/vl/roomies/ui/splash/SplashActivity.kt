package vl.roomies.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import vl.roomies.R
import vl.roomies.app.RoomiesApp.Companion.firebaseAuth
import vl.roomies.ui.home.HomeActivity
import vl.roomies.ui.log_in.LogInActivity

class SplashActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_splash)

		if (firebaseAuth.currentUser == null) {
			LogInActivity.start(this)
		} else {
			HomeActivity.start(this)
		}
	}
}
