package vl.roomies.data.models

import com.google.firebase.firestore.Exclude
import vl.roomies.R
import vl.roomies.app.RoomiesApp
import vl.roomies.data.source.currentUser

data class Contributor(val user: User, @get:Exclude var chippedIn: Boolean, var moneyPart: String, var isPaid: Boolean = false) {

	constructor(): this(currentUser, false, "0.0")

	val displayName
		@Exclude
		get() = if (user.id != currentUser.id) {
			user.name
		} else {
			user.name + " " + RoomiesApp.appContext.getString(R.string.hint_you)
		}
}