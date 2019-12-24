package vl.roomies.data.models

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sticker(
	var text: String,
	@DocumentId val id: String? = null): Parcelable {

	constructor(): this("")
}