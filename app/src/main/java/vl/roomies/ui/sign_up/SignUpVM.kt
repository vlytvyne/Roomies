package vl.roomies.ui.sign_up

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.floctopus.ui.common.BasicVM
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.UserProfileChangeRequest
import vl.roomies.app.RoomiesApp.Companion.firebaseAuth
import vl.roomies.ui.common.MutableActionLiveData
import vl.roomies.ui.common.fire

class SignUpVM: BasicVM() {

	val email = MutableLiveData("")
	val password = MutableLiveData("")
	val name = MutableLiveData("")

	val logInAction = MutableActionLiveData<Unit>()

	fun onSignUpClick() {
		startLoading()
		firebaseAuth.createUserWithEmailAndPassword(email.value!!, password.value!!)
			.addOnCompleteListener {
				task: Task<AuthResult> ->
				stopLoading()
				if (task.isSuccessful) {
					Log.d("TAGG", "success")
					val user = firebaseAuth.currentUser!!
					val profileUpdates = UserProfileChangeRequest.Builder()
						.setDisplayName(name.value!!)
						.build()
					user.updateProfile(profileUpdates).addOnCompleteListener {
						task: Task<Void> ->
						if (task.isSuccessful) {
							logInAction.fire()
						} else {

						}
					}
				} else {
					Log.d("TAGG", task.exception.toString())
				}
			}
	}

	companion object {

		fun create(activity: SignUpActivity) =
			ViewModelProviders.of(activity)[SignUpVM::class.java]
	}
}