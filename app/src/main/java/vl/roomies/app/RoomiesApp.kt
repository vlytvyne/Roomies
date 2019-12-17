package vl.roomies.app

import android.app.Application
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber

class RoomiesApp: Application() {

	override fun onCreate() {
		super.onCreate()
		firebaseAuth = FirebaseAuth.getInstance()
		appContext = applicationContext
		Timber.plant(Timber.DebugTree())
	}

	companion object {

		lateinit var firebaseAuth: FirebaseAuth
		lateinit var appContext: Context
	}
}