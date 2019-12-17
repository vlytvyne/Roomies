package vl.roomies.utils

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import vl.roomies.R

class NumberInputValidator(val input: LiveData<String>, val error: MutableLiveData<Int>): InputValidator {

	override var isValid = true
	private set

	var minValue: Double? = null
	@StringRes var minValueError: Int? = null

	var maxValue: Double? = null
	@StringRes var maxValueError: Int? = null

	private val inputObserver = Observer<String> { isNumberInputValid() }

	private fun isNumberInputValid() {
		if (input.value.isNullOrBlank()) {
			setError(R.string.error_field_cant_be_blank)
		} else if (minValue != null && input.value!!.toDouble() < minValue!!) {
			setError(minValueError)
		} else if (maxValue != null && input.value!!.toDouble() > maxValue!!) {
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