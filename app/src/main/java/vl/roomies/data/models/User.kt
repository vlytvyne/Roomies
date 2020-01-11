package vl.roomies.data.models

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(var name: String,
				var email: String,
				var cardNumber: String? = null,
				var bankLink: String? = null,
				@DocumentId val documentId: String? = null): Parcelable {

	constructor(): this("", "")
}