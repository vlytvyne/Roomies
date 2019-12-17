package vl.roomies.utils

interface InputValidator {

	val isValid: Boolean

	fun subscribe()

	fun unsubscribe()
}

val List<InputValidator>.isAllValid: Boolean
	get() {
		forEach {
			if (!it.isValid) {
				return false
			}
		}
		return true
	}



fun List<InputValidator>.unsubscribeAll() {
	forEach {
		it.unsubscribe()
	}
}

fun List<InputValidator>.subscribeAll() {
	forEach {
		it.subscribe()
	}
}