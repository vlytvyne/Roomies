package vl.roomies.ui.profile

import com.floctopus.ui.common.BasicVM
import vl.roomies.app.RoomiesApp

class ProfileVM: BasicVM() {

	fun logOut() {
		RoomiesApp.firebaseAuth.signOut()
		closeWindow()
	}

	companion object {

		fun create() = ProfileVM()
	}
}