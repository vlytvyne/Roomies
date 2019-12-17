package vl.roomies.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorStrRes")
fun TextInputLayout.setError(strRes: Int?) {
	error = if (strRes != null) context.getString(strRes) else null
}

@BindingAdapter("isVisible")
fun View.setVisibility(isVisible: Boolean) {
	visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun hideKeyboard(activity: Activity) {
	val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
	var view = activity.currentFocus
	if (view == null) {
		view = View(activity)
	}
	imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun hideKeyboard(fragment: Fragment) {
	hideKeyboard(fragment.activity!!)
}

fun hideKeyboardFrom(context: Context, view: View) {
	val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
	imm.hideSoftInputFromWindow(view.windowToken, 0)
}