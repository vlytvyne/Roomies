package vl.roomies.ui.fridge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeAdapter
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import kotlinx.android.synthetic.main.fragment_fridge.*
import kotlinx.android.synthetic.main.vh_sticker.view.*

import vl.roomies.R
import vl.roomies.utils.MarginItemDecoration

class FridgeFragment : Fragment() {

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_fridge, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupToolbar()
		setupRecycler()
	}

	private fun setupToolbar() {
		toolbar.title = getString(R.string.label_fridge)
	}

	private fun setupRecycler() {
		val adapter = StickerAdapter()
		adapter.dataSet = listOf("First sticker", "Second sticker", "Third sticker")
		adapter.notifyDataSetChanged()
		recyclerStickers.layoutManager = LinearLayoutManager(context!!)
		recyclerStickers.adapter = adapter
		recyclerStickers.orientation = DragDropSwipeRecyclerView.ListOrientation.VERTICAL_LIST_WITH_VERTICAL_DRAGGING
		recyclerStickers.orientation!!.removeSwipeDirectionFlag(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.RIGHT)
		recyclerStickers.addItemDecoration(MarginItemDecoration(8, 4))
	}

	companion object {

		fun newInstance() = FridgeFragment()
	}
}

class StickerAdapter: DragDropSwipeAdapter<String, StickerAdapter.VH>() {

	class VH(itemView: View) : DragDropSwipeAdapter.ViewHolder(itemView)

	override fun getViewHolder(itemView: View) = VH(itemView)

	override fun getViewToTouchToStartDraggingItem(item: String, viewHolder: VH, position: Int) = null

	override fun onBindViewHolder(item: String, viewHolder: VH, position: Int) {
		viewHolder.itemView.textStickerText.text = item
	}

}
