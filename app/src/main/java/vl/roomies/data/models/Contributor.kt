package vl.roomies.data.models

import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize
import vl.roomies.R
import vl.roomies.app.RoomiesApp.Companion.appContext
import vl.roomies.data.source.currentUser
import java.math.BigDecimal

@Parcelize
data class Contributor(val name: String,
					   val userId: String,
					   @get:Exclude var chippedIn: Boolean,
					   var moneyPart: String,
					   var isPaid: Boolean = false): Parcelable {

	constructor(): this("", "", false, "0")

	val displayName
		@Exclude
		get() = if (userId != currentUser.documentId) name else name + " " + appContext.getString(R.string.hint_you)

	val formattedMoneyPart
		@Exclude
		get() = BigDecimal(moneyPart).stripTrailingZeros().toPlainString()
}