package vl.roomies.ui.log_in

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.floctopus.ui.common.BasicVM
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import vl.roomies.app.RoomiesApp.Companion.firebaseAuth
import vl.roomies.ui.common.MutableActionLiveData
import vl.roomies.ui.common.fire

class LogInVM: BasicVM() {

	val email = MutableLiveData("")
	val password = MutableLiveData("")

	val logInAction = MutableActionLiveData<Unit>()

	val startSignUp = MutableActionLiveData<Unit>()

	fun onLogInClick() {
		startLoading()
		firebaseAuth.signInWithEmailAndPassword(email.value!!, password.value!!)
			.addOnCompleteListener {
				task: Task<AuthResult> ->
				stopLoading()
				if (task.isSuccessful) {
					logInAction.fire()
				} else {

				}
			}
	}

	fun onSignUpClick() {
		startSignUp.fire()
	}

	companion object {

		fun create(activity: LogInActivity) =
			ViewModelProviders.of(activity)[LogInVM::class.java]
	}
}