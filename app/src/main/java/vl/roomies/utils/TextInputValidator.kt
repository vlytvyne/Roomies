package vl.roomies.utils

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import vl.roomies.R

open class TextInputValidator(private val input: LiveData<String>, private val error: MutableLiveData<Int?>): InputValidator {

	private var isFirstObserverCall = true

	override var isValid = true
		get() {
			isTextInputValid(input.value)
			return field
		}
		protected set

	var minLen: Int? = null
	@StringRes
	var minLenError: Int? = null

	var maxLen: Int? = null
	@StringRes var maxLenError: Int? = R.string.error_too_long

	var canBeBlank: Boolean = false

	private val inputObserver = Observer<String> {
		if (!isFirstObserverCall) {
			isTextInputValid(it)
		} else {
			isFirstObserverCall = false
		}
	}

	open protected fun isTextInputValid(input: String?) {
		if (input.isNullOrBlank() && !canBeBlank) {
			setError(R.string.error_field_cant_be_blank)
		} else if (minLen != null && input!!.length < minLen!!) {
			setError(minLenError)
		} else if (maxLen != null && input!!.length >= maxLen!!) {
			setError(maxLenError)
		} else {
			setError(null)
		}
	}

	fun setError(errorStrRes: Int?) {
		isValid = errorStrRes == null
		error.value = errorStrRes
	}

	override fun subscribe() {
		input.observeForever(inputObserver)
	}

	override fun unsubscribe() {
		input.removeObserver(inputObserver)
	}
}