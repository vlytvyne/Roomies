package vl.roomies.utils

import android.view.View
import androidx.annotation.StringRes
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.BaseTransientBottomBar.BaseCallback.DISMISS_EVENT_ACTION
import com.google.android.material.snackbar.Snackbar
import vl.roomies.R

val swipeLeftDragUpDownOrientation: DragDropSwipeRecyclerView.ListOrientation by lazy {
	val orientation = DragDropSwipeRecyclerView.ListOrientation.VERTICAL_LIST_WITH_VERTICAL_DRAGGING
	orientation.removeSwipeDirectionFlag(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.RIGHT)
	orientation
}

//Library has a bug which is why I had to do the following. An issue is here: https://github.com/ernestoyaquello/DragDropSwipeRecyclerview/issues/38
val swipeLeftOrientation: DragDropSwipeRecyclerView.ListOrientation by lazy {
	val orientation = DragDropSwipeRecyclerView.ListOrientation.VERTICAL_LIST_WITH_UNCONSTRAINED_DRAGGING
	orientation.removeSwipeDirectionFlag(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.RIGHT)
	orientation.removeDragDirectionFlag(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.UP)
	orientation.removeDragDirectionFlag(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.DOWN)
	orientation.removeDragDirectionFlag(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.RIGHT)
	orientation.removeDragDirectionFlag(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.LEFT)
	orientation
}

fun createUndoDeleteSnack(view: View, @StringRes deleteDescStrRes: Int, undoAction: () -> Unit, deleteAction: () -> Unit): Snackbar =
	Snackbar.make(view, deleteDescStrRes, Snackbar.LENGTH_LONG)
		.setAction(R.string.btn_undo) { undoAction.invoke() }
		.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
			override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
				if (event != DISMISS_EVENT_ACTION) {
					deleteAction.invoke()
				}
			}
		})
		.apply { animationMode = Snackbar.ANIMATION_MODE_SLIDE }

