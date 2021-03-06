package vl.roomies.ui.log_in

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.floctopus.ui.common.BasicVM
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import timber.log.Timber
import vl.roomies.R
import vl.roomies.data.constants.IntegerConstants
import vl.roomies.data.source.FirebaseRepository
import vl.roomies.ui.common.MutableActionLiveData
import vl.roomies.ui.common.fire
import vl.roomies.utils.TextInputValidator
import vl.roomies.utils.isAllValid
import vl.roomies.utils.subscribeAll
import vl.roomies.utils.unsubscribeAll
import java.lang.Exception

class LogInVM: BasicVM() {

	val email = MutableLiveData("")
	val password = MutableLiveData("")

	val emailError = MutableLiveData<@StringRes Int?>()
	val passwordError = MutableLiveData<@StringRes Int?>()

	val logInAction = MutableActionLiveData<Unit>()
	val startSignUpAction = MutableActionLiveData<Unit>()

	private val emailValidator = TextInputValidator(email, emailError)
	private val passwordValidator = TextInputValidator(password, passwordError).apply {
		minLen = IntegerConstants.minPasswordLen
		minLenError = R.string.error_at_least_6_symbols
	}

	private val validators = listOf(emailValidator, passwordValidator)

	init {
		validators.subscribeAll()
	}

	override fun onCleared() {
		super.onCleared()
		validators.unsubscribeAll()
	}

	fun onLogInClick() {
		if (validators.isAllValid) {
			hideKeyboard()
			startLogIn()
		}
	}

	private fun startLogIn() {
		startLoading()
		disableInput()
		FirebaseRepository.logIn(email.value!!, password.value!!)
			.addOnCompleteListener {
				stopLoading()
				enableInput()
			}
			.addOnSuccessListener { logInAction.fire() }
			.addOnFailureListener { handleLogInException(it) }
	}

	private fun handleLogInException(exception: Exception) {
		Timber.d(exception)
		when (exception) {
			is FirebaseAuthInvalidUserException -> showSnackError(R.string.error_user_doesnt_exist)
			is FirebaseAuthInvalidCredentialsException -> passwordValidator.setError(R.string.error_wrong_password)
			is FirebaseTooManyRequestsException -> showSnackError(R.string.error_too_many_requests_try_later)
			else -> showSnackError(R.string.error_unknown)
		}
	}

	fun onSignUpClick() {
		startSignUpAction.fire()
	}

	companion object {

		fun create(activity: LogInActivity) =
			ViewModelProviders.of(activity)[LogInVM::class.java]
	}
}