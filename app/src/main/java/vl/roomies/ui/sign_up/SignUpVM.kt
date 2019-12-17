package vl.roomies.ui.sign_up

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.floctopus.ui.common.BasicVM
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.UserProfileChangeRequest
import timber.log.Timber
import vl.roomies.R
import vl.roomies.app.RoomiesApp.Companion.firebaseAuth
import vl.roomies.data.IntegerConstants
import vl.roomies.ui.common.MutableActionLiveData
import vl.roomies.ui.common.fire
import vl.roomies.utils.TextInputValidator
import vl.roomies.utils.isAllValid
import vl.roomies.utils.subscribeAll
import vl.roomies.utils.unsubscribeAll
import java.lang.Exception

class SignUpVM: BasicVM() {

	val email = MutableLiveData("")
	val password = MutableLiveData("")
	val name = MutableLiveData("")

	val emailError = MutableLiveData<@StringRes Int?>()
	val passwordError = MutableLiveData<@StringRes Int?>()
	val nameError = MutableLiveData<@StringRes Int?>()
	val snackError = MutableActionLiveData<@StringRes Int>()

	val logInAction = MutableActionLiveData<Unit>()

	private val emailValidator = TextInputValidator(email, emailError)
	private val passwordValidator = TextInputValidator(password, passwordError).apply {
		minLen = IntegerConstants.minPasswordLen
		minLenError = R.string.error_at_least_6_symbols
	}
	private val nameValidator = TextInputValidator(name, nameError)

	private val validators = listOf(emailValidator, passwordValidator, nameValidator)

	init {
		validators.subscribeAll()
	}

	override fun onCleared() {
		super.onCleared()
		validators.unsubscribeAll()
	}

	fun onSignUpClick() {
		if (validators.isAllValid) {
			hideKeyboard()
			signUp()
		}
	}

	private fun signUp() {
		startLoading()
		firebaseAuth.createUserWithEmailAndPassword(email.value!!, password.value!!)
			.addOnCompleteListener { stopLoading() }
			.addOnSuccessListener { attachNameToUser() }
			.addOnFailureListener { handleSignUpException(it) }
	}

	private fun attachNameToUser() {
		val user = firebaseAuth.currentUser!!
		val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(name.value!!).build()
		user.updateProfile(profileUpdates)
			.addOnSuccessListener { logInAction.fire() }
			.addOnFailureListener { handleAttachNameToUserException(it) }
	}

	private fun handleSignUpException(exception: Exception) {
		when(exception) {
			is FirebaseAuthUserCollisionException -> snackError.fire(R.string.error_email_already_used)
		}
	}

	private fun handleAttachNameToUserException(exception: Exception) {
	}

	companion object {

		fun create(activity: SignUpActivity) =
			ViewModelProviders.of(activity)[SignUpVM::class.java]
	}
}