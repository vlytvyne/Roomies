package vl.roomies.ui.purchases.creation

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeAdapter
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import kotlinx.android.synthetic.main.activity_purchase_creation.*
import kotlinx.android.synthetic.main.vh_purchase_creation_payer.view.*
import vl.roomies.R

class PurchaseCreationActivity : AppCompatActivity() {

	private val adapter = PayerAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_purchase_creation)
		setupToolbar()
		setupRecycler()
	}

	private fun setupToolbar() {
		toolbar.title = getString(R.string.label_create_purchase)
		toolbar.setNavigationIcon(R.drawable.ic_up_navigation_arrow)
		toolbar.setNavigationOnClickListener { finish() }
		toolbar.inflateMenu(R.menu.create)
	}

	private fun setupRecycler() {
		recyclerPayers.layoutManager = LinearLayoutManager(this)
		adapter.dataSet = listOf("You", "Gena", "Denys")
		recyclerPayers.adapter = adapter
		recyclerPayers.orientation?.removeSwipeDirectionFlag(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.RIGHT)
		recyclerPayers.orientation?.removeSwipeDirectionFlag(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.LEFT)
		recyclerPayers.orientation?.removeDragDirectionFlag(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.UP)
		recyclerPayers.orientation?.removeDragDirectionFlag(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.DOWN)
	}

	companion object {

		fun start(activity: Activity) {
			val intent = Intent(activity, PurchaseCreationActivity::class.java)
			activity.startActivity(intent)
		}
	}
}

private class PayerAdapter: DragDropSwipeAdapter<String, PayerAdapter.VH>() {

	class VH(itemView: View) : DragDropSwipeAdapter.ViewHolder(itemView)

	override fun getViewHolder(itemView: View) = VH(itemView)

	override fun getViewToTouchToStartDraggingItem(item: String, viewHolder: VH, position: Int) = null

	override fun onBindViewHolder(item: String, viewHolder: VH, position: Int) {
		viewHolder.itemView.chbPayerName.text = item
	}

}