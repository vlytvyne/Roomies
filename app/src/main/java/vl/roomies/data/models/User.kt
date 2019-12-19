package vl.roomies.data.models

data class User(val name: String,
				val email: String,
				val cardNumber: String? = null,
				val bankLink: String? = null) {

	constructor(): this("", "")
}