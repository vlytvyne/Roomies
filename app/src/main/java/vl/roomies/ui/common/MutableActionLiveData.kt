package vl.roomies.ui.common

import androidx.annotation.MainThread

class MutableActionLiveData<T> : ActionLiveData<T>() {

	@MainThread
	fun fire(data: T) {
		value = data
	}
}

fun MutableActionLiveData<Unit>.fire() {
	fire(Unit)
}