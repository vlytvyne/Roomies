package vl.roomies.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.floctopus.ui.common.BasicVM
import vl.roomies.R
import vl.roomies.app.RoomiesApp.Companion.appContext
import vl.roomies.data.source.FirebaseRepository
import vl.roomies.data.source.currentUser
import vl.roomies.ui.common.MutableActionLiveData
import vl.roomies.ui.common.fire

class ProfileVM: BasicVM() {

	val changeNameAction = MutableActionLiveData<Unit>()
	val changeBankDetailsAction = MutableActionLiveData<Unit>()

	val name = MutableLiveData<String>()
	val email = MutableLiveData<String>()
	val cardNumber = MutableLiveData<String>()
	val bankLink = MutableLiveData<String>()

	init {
		refreshInfo()
	}

	fun logOut() {
		FirebaseRepository.logOut()
		closeWindow()
	}

	fun onChangeNameClick() {
		changeNameAction.fire()
	}

	fun onChangeBankDetailsClick() {
		changeBankDetailsAction.fire()
	}

	fun refreshInfo() {
		name.value = currentUser.name
		email.value = currentUser.email
		cardNumber.value = currentUser.cardNumber ?: appContext.getString(R.string.placeholder_not_set)
		bankLink.value = currentUser.bankLink ?: appContext.getString(R.string.placeholder_not_set)
	}

	companion object {

		fun create(fragment: ProfileFragment) =
			ViewModelProviders.of(fragment)[ProfileVM::class.java]
	}
}