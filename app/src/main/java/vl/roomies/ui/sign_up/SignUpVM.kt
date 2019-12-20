package vl.roomies.ui.sign_up

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.floctopus.ui.common.BasicVM
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import timber.log.Timber
import vl.roomies.R
import vl.roomies.data.constants.IntegerConstants
import vl.roomies.data.models.User
import vl.roomies.data.source.FirebaseRepository
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
		disableInput()
		FirebaseRepository.signUp(email.value!!, password.value!!)
			.addOnSuccessListener { attachNameToUser() }
			.addOnFailureListener {
				enableInput()
				stopLoading()
				handleSignUpException(it)
			}
	}

	private fun attachNameToUser() {
		val user = User(name = name.value!!,
						email = email.value!!)
		FirebaseRepository.updateUserInfo(user)
			.addOnCompleteListener {
				stopLoading()
				enableInput()
			}
			.addOnSuccessListener { logInAction.fire() }
			.addOnFailureListener { handleAttachNameToUserException(it) }
	}

	private fun handleSignUpException(exception: Exception) {
		when(exception) {
			is FirebaseAuthUserCollisionException -> showSnackError(R.string.error_email_already_used)
			else -> showSnackError(R.string.error_unknown)
		}
	}

	private fun handleAttachNameToUserException(exception: Exception) {
		showSnackError(R.string.error_unknown)
	}

	companion object {

		fun create(activity: SignUpActivity) =
			ViewModelProviders.of(activity)[SignUpVM::class.java]
	}
}