package vl.roomies.utils

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("isVisible")
fun View.setVisibility(isVisible: Boolean) {
	visibility = if (isVisible) {
		View.VISIBLE
	} else {
		View.GONE
	}
}