package vl.roomies.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import vl.roomies.app.RoomiesApp.Companion.appContext

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

fun convertPixelsToDp(px: Float) =
	px / (appContext.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

fun convertDpToPixel( dp: Float) =
	dp * (appContext.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)