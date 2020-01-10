package vl.roomies.utils

import android.view.View
import android.widget.Switch
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorStrRes")
fun TextInputLayout.setError(strRes: Int?) {
	error = if (strRes != null) context.getString(strRes) else null
}

@BindingAdapter("isVisible")
fun View.setVisibility(isVisible: Boolean) {
	visibility = if (isVisible) View.VISIBLE else View.GONE
}
