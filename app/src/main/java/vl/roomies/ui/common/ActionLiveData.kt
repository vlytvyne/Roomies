package vl.roomies.ui.common

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

open class ActionLiveData<T> : LiveData<T>() {

	@MainThread
	override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
		if (hasObservers()) {
			throw Throwable("Only one observer at a time may subscribe to an ActionLiveData")
		}

		super.observe(owner, Observer { data ->
			if (data != null) {
				observer.onChanged(data)
				value = null
			}
			// We set the value to null straight after emitting the change to the observer
			// This means that the state of the data will always be null / non existent
			// It will only be available to the observer in its callback and since we do not emit null values
			// the observer never receives a null value and any observers resuming do not receive the last event.
			// Therefore it only emits to the observer the single action so you are free to show messages over and over again
			// Or launch an activity/dialog or anything that should only happen once per action / click :).
		})
	}
}