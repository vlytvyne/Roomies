package vl.roomies.utils

import android.text.Editable

class CutDigitsAfterDotWatcher(private val digitsAfterDot: Int): NotObsessiveTextWatcher {

	override fun afterTextChanged(discount: Editable?) {
		val parts = discount!!.split('.')
		if (parts.size > 1 && parts[1].length > digitsAfterDot) {
			discount.delete(discount.length - 1, discount.length)
		}
	}
}