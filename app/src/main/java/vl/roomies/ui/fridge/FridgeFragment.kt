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
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnItemDragListener
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnItemSwipeListener
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_fridge.*
import kotlinx.android.synthetic.main.vh_sticker.view.*

import vl.roomies.R
import vl.roomies.data.models.Sticker
import vl.roomies.ui.fridge.creation.CreateEditStickerActivity
import vl.roomies.ui.fridge.creation.Mode
import vl.roomies.utils.HideFabOnScrollListener
import vl.roomies.utils.MarginItemDecoration

private const val RC_CREATE_STICKER = 1
private const val RC_EDIT_STICKER = 2

class FridgeFragment : Fragment() {

	private lateinit var viewmodel: FridgeVM

	private val adapter = StickerAdapter()

	private val snackDragHint: Snackbar by lazy {
		Snackbar.make(toolbar, R.string.hint_drag_sticker_on_top_to_pin_it, Snackbar.LENGTH_INDEFINITE)
			.apply { animationMode = Snackbar.ANIMATION_MODE_SLIDE }
	}
	private val snackStickerPinned: Snackbar by lazy {
		Snackbar.make(toolbar, R.string.hint_sticker_is_pinned, Snackbar.LENGTH_SHORT)
			.apply { animationMode = Snackbar.ANIMATION_MODE_SLIDE }
	}
	private val snackStickerUnpinned: Snackbar by lazy {
		Snackbar.make(toolbar, R.string.hint_sticker_is_unpinned, Snackbar.LENGTH_SHORT)
			.apply { animationMode = Snackbar.ANIMATION_MODE_SLIDE }
	}

	private val snackUndoDelete: Snackbar by lazy {
		Snackbar.make(toolbar, R.string.hint_sticker_is_deleted, Snackbar.LENGTH_LONG)
			.setAction(R.string.btn_undo) { viewmodel.refreshStickers() }
			.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
				override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
					if (event != DISMISS_EVENT_ACTION) {
						stickerToDelete?.let { viewmodel.deleteSticker(it) }
					}
				}
			})
			.apply { animationMode = Snackbar.ANIMATION_MODE_SLIDE }
	}

	private var stickerToDelete: Sticker? = null

	private val onItemSwipeListener = object : OnItemSwipeListener<Sticker> {
		override fun onItemSwiped(position: Int, direction: OnItemSwipeListener.SwipeDirection, item: Sticker): Boolean {
			snackUndoDelete.show()
			stickerToDelete = item
			return false
		}
	}

	private val onItemDragListener = object : OnItemDragListener<Sticker> {
		override fun onItemDragged(previousPosition: Int, newPosition: Int, item: Sticker) {
			snackDragHint.show()
			if (newPosition == 0) {
				snackDragHint.setText(R.string.hint_drop_sticker_here_to_pin_it)
			} else {
				snackDragHint.setText(R.string.hint_drag_sticker_on_top_to_pin_it)
			}
		}

		override fun onItemDropped(initialPosition: Int, finalPosition: Int, item: Sticker) {
			if (initialPosition == finalPosition) {
				return
			}
			snackDragHint.dismiss()
			if (finalPosition == 0) {
				snackStickerPinned.show()
				viewmodel.pinStickerAtTop(item)
			} else if (initialPosition == 0) {
				snackStickerUnpinned.show()
				viewmodel.unpinStickerAtTop(item)
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
		recyclerStickers.dragListener = onItemDragListener
		recyclerStickers.scrollListener = HideFabOnScrollListener(fabCreateSticker)
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
