package vl.roomies.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*
import vl.roomies.R
import vl.roomies.app.RoomiesApp.Companion.firebaseAuth

class HomeActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_home)

		btnLogOut.setOnClickListener {
			firebaseAuth.signOut()
			finishAffinity()
		}
	}

	override fun onBackPressed() {
		finishAffinity()
	}

	companion object {
		 fun start(activity: AppCompatActivity) {
			 val intent = Intent(activity, HomeActivity::class.java)
			 intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
			 activity.startActivity(intent)
		 }
	}
}
