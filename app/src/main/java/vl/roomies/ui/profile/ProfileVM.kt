package vl.roomies.ui.profile

import com.floctopus.ui.common.BasicVM
import vl.roomies.data.source.FirebaseRepository
import vl.roomies.ui.common.MutableActionLiveData
import vl.roomies.ui.common.fire

class ProfileVM: BasicVM() {

	val changeNameAction = MutableActionLiveData<Unit>()

	fun logOut() {
		FirebaseRepository.logOut()
		closeWindow()
	}

	fun onChangeNameClick() {
		changeNameAction.fire()
	}

	companion object {

		fun create() = ProfileVM()
	}
}