package vl.roomies.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val verticalSpaceInDp: Int, private val horizontalSpaceInDp: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
		with(outRect) {
			if (parent.getChildAdapterPosition(view) == 0) {
				top = convertDpToPixel(verticalSpaceInDp.toFloat()).toInt()
			}
			left = convertDpToPixel(horizontalSpaceInDp.toFloat()).toInt()
			right = convertDpToPixel(horizontalSpaceInDp.toFloat()).toInt()
			bottom = convertDpToPixel(verticalSpaceInDp.toFloat()).toInt()
		}
	}
}