package vl.roomies.data.models

import vl.roomies.R
import vl.roomies.app.RoomiesApp
import vl.roomies.data.source.currentUser

data class Contributor(val user: User, var chippedIn: Boolean, var moneyPart: String, var isPaid: Boolean = false) {

	val displayName
		get() = if (user.id != currentUser.id) {
			user.name
		} else {
			user.name + " " + RoomiesApp.appContext.getString(R.string.hint_you)
		}
}