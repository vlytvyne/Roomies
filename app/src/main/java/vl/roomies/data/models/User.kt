package vl.roomies.data.models

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize
import vl.roomies.R
import vl.roomies.app.RoomiesApp.Companion.appContext
import vl.roomies.data.source.currentUser

@Parcelize
data class User(var name: String,
				var email: String,
				var cardNumber: String? = null,
				var bankLink: String? = null,
				@DocumentId val documentId: String? = null): Parcelable {

	constructor(): this("", "")

	val displayName
		@Exclude
		get() = if (documentId != currentUser.documentId) name else name + " " + appContext.getString(R.string.hint_you)

	val displayBankLink
		@Exclude
		get() = if (bankLink.isNullOrBlank()) appContext.getString(R.string.placeholder_not_set) else bankLink

	val displayCardNumber
		@Exclude
		get() = if (cardNumber.isNullOrBlank()) appContext.getString(R.string.placeholder_not_set) else cardNumber

	val hasCardNumber
		@Exclude
		get() = !cardNumber.isNullOrBlank()
}