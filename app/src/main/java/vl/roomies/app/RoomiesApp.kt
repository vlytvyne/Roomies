package vl.roomies.app

import android.app.Application
import com.google.firebase.auth.FirebaseAuth

class RoomiesApp: Application() {

	override fun onCreate() {
		super.onCreate()
		firebaseAuth = FirebaseAuth.getInstance()
	}

	companion object {

		lateinit var firebaseAuth: FirebaseAuth
	}
}