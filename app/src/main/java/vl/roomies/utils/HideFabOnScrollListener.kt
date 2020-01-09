package vl.roomies.utils

import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnListScrollListener
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HideFabOnScrollListener(private val fab: FloatingActionButton): OnListScrollListener {
	override fun onListScrollStateChanged(scrollState: OnListScrollListener.ScrollState) {}

	override fun onListScrolled(scrollDirection: OnListScrollListener.ScrollDirection, distance: Int) {
		if (scrollDirection == OnListScrollListener.ScrollDirection.DOWN) {
			fab.hide()
		} else {
			fab.show()
		}
	}
}