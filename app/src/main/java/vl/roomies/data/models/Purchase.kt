package vl.roomies.data.models

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize
import vl.roomies.R
import vl.roomies.app.RoomiesApp.Companion.appContext
import vl.roomies.data.source.currentUser
import java.math.BigDecimal

@Parcelize
data class Purchase(var buyer: User,
					var buyerId: String,
					var title: String,
					var description: String,
					var cost: String,
					var contributors: List<Contributor>,
					var contributorsId: List<String>,
					var timeCreated: Timestamp,
					@DocumentId val documentId: String? = null): Parcelable {

	constructor(): this(currentUser, "","", "", "0", listOf(), listOf(), Timestamp.now())

	val isPaidByAll
		@Exclude
		get() = contributors.all { it.isPaid }

	val displayDescription
		@Exclude
		get() = if (description.isBlank()) appContext.getString(R.string.label_no_description) else description

	val myContribution
		@Exclude
		get() = contributors.find { it.userId == currentUser.documentId }

	val fomattedCost
		@Exclude
		get() = BigDecimal(cost).stripTrailingZeros().toPlainString()
}
