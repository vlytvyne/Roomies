package vl.roomies.ui.profile.change_name

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.floctopus.ui.common.BasicVM
import vl.roomies.R
import vl.roomies.data.source.FirebaseRepository
import vl.roomies.data.source.currentUser
import vl.roomies.utils.TextInputValidator

class ChangeNameVM: BasicVM() {

	//@2way db
	val newName = MutableLiveData<String>(currentUser.name)

	val newNameError = MutableLiveData<@StringRes Int?>()

	private val newNameValidator = TextInputValidator(newName, newNameError)

	init {
		newNameValidator.subscribe()
	}

	override fun onCleared() {
		super.onCleared()
		newNameValidator.unsubscribe()
	}

	fun onSaveClick() {
		if (newNameValidator.isValid) {
			saveNewName()
		}
	}

	private fun saveNewName() {
		startLoading()
		disableInput()
		currentUser.name = newName.value!!
		FirebaseRepository.updateUserInfo(currentUser)
			.addOnSuccessListener { closeWindow() }
			.addOnFailureListener {
				stopLoading()
				enableInput()
				showSnackError(R.string.error_unknown)
			}
	}

	companion object {

		fun create(activity: ChangeNameActivity) =
			ViewModelProviders.of(activity)[ChangeNameVM::class.java]
	}
}