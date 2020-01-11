package vl.roomies.data.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import vl.roomies.R
import vl.roomies.app.RoomiesApp.Companion.appContext
import vl.roomies.data.source.currentUser

data class Purchase(var buyer: User,
					var title: String,
					var description: String,
					var cost: String,
					var contributors: List<Contributor>,
					var timeCreated: Timestamp,
					@DocumentId val id: String? = null) {

	constructor(): this(currentUser, "", "", "0", listOf(), Timestamp.now())

	val isPaidByAll
		@Exclude
		get() = contributors.all { it.isPaid }

	val displayDescription
		@Exclude
		get() = if (description.isBlank()) appContext.getString(R.string.label_no_description) else description
}
