package vl.roomies.utils

import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeAdapter

fun <T, V: DragDropSwipeAdapter.ViewHolder> DragDropSwipeAdapter<T, V>.addAll(data: List<T>) {
	data.forEach { addItem(it) }
}