package vl.roomies.utils

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import vl.roomies.R

class NumberInputValidator(private val input: LiveData<String>, private val error: MutableLiveData<Int?>): InputValidator {

	private var isFirstObserverCall = true

	override var isValid = true
		get() {
			isNumberInputValid(input.value!!)
			return field
		}
		protected set

	var minValue: Double? = null
	@StringRes var minValueError: Int? = null

	var maxValue: Double? = null
	@StringRes var maxValueError: Int? = null

	private val inputObserver = Observer<String> {
		if (!isFirstObserverCall) {
			isNumberInputValid(it)
		} else {
			isFirstObserverCall = false
		}
	}

	private fun isNumberInputValid(number: String) {
		if (number.isNullOrBlank() || !number.first().isDigit()) {
			setError(R.string.error_field_cant_be_blank)
		} else if (minValue != null && number!!.toDouble() < minValue!!) {
			setError(minValueError)
		} else if (maxValue != null && number!!.toDouble() > maxValue!!) {
			setError(maxValueError)
		} else {
			setError(null)
		}
	}

	private fun setError(errorStrRes: Int?) {
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