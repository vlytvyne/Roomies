package vl.roomies.ui.fridge

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeAdapter
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnItemSwipeListener
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnListScrollListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_fridge.*
import kotlinx.android.synthetic.main.vh_sticker.view.*

import vl.roomies.R
import vl.roomies.data.models.Sticker
import vl.roomies.ui.fridge.creation.CreateEditStickerActivity
import vl.roomies.ui.fridge.creation.Mode
import vl.roomies.utils.MarginItemDecoration

private const val RC_CREATE_STICKER = 1
private const val RC_EDIT_STICKER = 2

class FridgeFragment : Fragment() {

	private lateinit var viewmodel: FridgeVM

	private val adapter = StickerAdapter()

	private val onItemSwipeListener = object : OnItemSwipeListener<Sticker> {
		override fun onItemSwiped(position: Int, direction: OnItemSwipeListener.SwipeDirection, item: Sticker): Boolean {
			viewmodel.deleteSticker(item)
			return false
		}
	}

	private val onListScrollListener = object : OnListScrollListener {
		override fun onListScrollStateChanged(scrollState: OnListScrollListener.ScrollState) {}

		override fun onListScrolled(scrollDirection: OnListScrollListener.ScrollDirection, distance: Int) {
			if (scrollDirection == OnListScrollListener.ScrollDirection.DOWN) {
				fabCreateSticker.hide()
			} else {
				fabCreateSticker.show()
			}
		}
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		viewmodel = FridgeVM.create(this)
		setupVMObservers()
	}

	private fun setupVMObservers() {
		viewmodel.setStickersAction.observe(this, Observer {
			adapter.dataSet = it
		})
		viewmodel.isLoading.observe(this, Observer {
			progressBar.isVisible = it
		})
		viewmodel.snackError.observe(this, Observer {
			Snackbar.make(toolbar, it, Snackbar.LENGTH_LONG).show()
		})
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_fridge, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupToolbar()
		setupRecycler()
		fabCreateSticker.setOnClickListener { CreateEditStickerActivity.startForResult(this, Mode.STICKER_CREATE, RC_CREATE_STICKER) }
	}

	private fun setupToolbar() {
		toolbar.title = getString(R.string.label_fridge)
	}

	private fun setupRecycler() {
		recyclerStickers.layoutManager = LinearLayoutManager(context!!)
		recyclerStickers.adapter = adapter
		adapter.onStickerClick = { CreateEditStickerActivity.startForResult(this, Mode.STICKER_EDIT, RC_EDIT_STICKER, it) }
		recyclerStickers.orientation = DragDropSwipeRecyclerView.ListOrientation.VERTICAL_LIST_WITH_VERTICAL_DRAGGING
		recyclerStickers.orientation!!.removeSwipeDirectionFlag(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.RIGHT)
		recyclerStickers.addItemDecoration(MarginItemDecoration(8, 4))
		recyclerStickers.swipeListener = onItemSwipeListener
		recyclerStickers.scrollListener = onListScrollListener
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		if (resultCode == RESULT_OK) {
			viewmodel.refreshStickers()
		}
	}

	companion object {

		fun newInstance() = FridgeFragment()
	}
}

private class StickerAdapter: DragDropSwipeAdapter<Sticker, StickerAdapter.VH>() {

	var onStickerClick: ((Sticker) -> Unit)? = null

	class VH(itemView: View) : DragDropSwipeAdapter.ViewHolder(itemView)

	override fun getViewHolder(itemView: View) = VH(itemView)

	override fun getViewToTouchToStartDraggingItem(item: Sticker, viewHolder: VH, position: Int) = null

	override fun onBindViewHolder(item: Sticker, viewHolder: VH, position: Int) {
		viewHolder.itemView.textStickerText.text = item.text
		viewHolder.itemView.setOnClickListener { onStickerClick?.invoke(item) }
	}

}
