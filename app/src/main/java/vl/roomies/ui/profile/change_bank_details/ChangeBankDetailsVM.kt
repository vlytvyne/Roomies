package vl.roomies.ui.profile.change_bank_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.floctopus.ui.common.BasicVM
import vl.roomies.R
import vl.roomies.data.source.FirebaseRepository
import vl.roomies.data.source.currentUser

class ChangeBankDetailsVM: BasicVM() {

	//2way db
	val cardNumber = MutableLiveData<String>(currentUser.cardNumber ?: "")
	val bankLink = MutableLiveData<String>(currentUser.bankLink ?: "")

	fun onSaveClick() {
		startLoading()
		disableInput()
		currentUser.cardNumber = if (cardNumber.value.isNullOrBlank()) null else cardNumber.value!!
		currentUser.bankLink = if (bankLink.value.isNullOrBlank()) null else bankLink.value!!
		FirebaseRepository.updateUserInfo(currentUser)
			.addOnSuccessListener { closeWindow() }
			.addOnFailureListener {
				stopLoading()
				enableInput()
				showSnackError(R.string.error_unknown)
			}
	}

	companion object {

		fun create(activity: ChangeBankDetailsActivity) =
			ViewModelProviders.of(activity)[ChangeBankDetailsVM::class.java]
	}
}