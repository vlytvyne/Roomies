package vl.roomies.data.models

import com.google.firebase.firestore.DocumentId

data class User(var name: String,
				var email: String,
				var cardNumber: String? = null,
				var bankLink: String? = null,
				@DocumentId val id: String? = null) {

	constructor(): this("", "")
}