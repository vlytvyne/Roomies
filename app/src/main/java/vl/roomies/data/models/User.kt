package vl.roomies.data.models

data class User(var name: String,
				var email: String,
				var cardNumber: String? = null,
				var bankLink: String? = null) {

	constructor(): this("", "")
}