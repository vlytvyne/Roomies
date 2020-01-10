package vl.roomies.ui.fridge.creation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vl.roomies.data.models.Contributor
import vl.roomies.data.models.User
import vl.roomies.databinding.VhPurchaseCreationContributorBinding
import vl.roomies.utils.CutDigitsAfterDotWatcher

class ContributorAdapter: RecyclerView.Adapter<VH>() {

	private var contributors = listOf<Contributor>()
	private var allViewsDisabled = false

	val chippedInContributors
		get() = contributors.filter { it.chippedIn }

	fun setUsers(users: List<User>) {
		contributors = users.map { Contributor(it, true, "0") }
		notifyDataSetChanged()
	}

	fun addAllToContributors() {
		contributors.forEach { it.chippedIn = true }
		notifyDataSetChanged()
	}

	fun removeAllFromContributors() {
		contributors.forEach { it.chippedIn = false }
		contributors.forEach { it.moneyPart = "0" }
		notifyDataSetChanged()
	}

	fun onPayEqually(cost: Double) {
		val contributorsToChipIn = contributors.filter { it.chippedIn }
		val costForOne = cost / contributorsToChipIn.size
		contributors.forEach { it.moneyPart = "0" }
		contributorsToChipIn.forEach { it.moneyPart = costForOne.toString() }
		allViewsDisabled = true
		notifyDataSetChanged()
	}

	fun offPayEqually() {
		allViewsDisabled = false
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
		val inflater = LayoutInflater.from(parent.context)
		return VH(VhPurchaseCreationContributorBinding.inflate(inflater, parent, false))
	}

	override fun getItemCount(): Int = contributors.size

	override fun onBindViewHolder(holder: VH, position: Int) {
		holder.bind(contributors[position])
		if (allViewsDisabled) {
			holder.disableAllViews()
		} else {
			holder.enableAllViews()
		}
	}
}

class VH(private val binding: VhPurchaseCreationContributorBinding): RecyclerView.ViewHolder(binding.root) {

	//idk why but OnCheckChangedListener works laggy only after some actions. So I had to do some sex with clickListener
	fun bind(contributor: Contributor) {
		binding.etCost.addTextChangedListener(CutDigitsAfterDotWatcher(2))
		binding.contributor = contributor
		binding.chbContributorName.setOnClickListener {
			binding.etCost.isEnabled = binding.chbContributorName.isChecked
			if (!binding.chbContributorName.isChecked) {
				binding.etCost.setText("0")
			}
		}
	}

	fun disableAllViews() {
		binding.chbContributorName.isEnabled = false
		binding.etCost.isEnabled = false
	}

	fun enableAllViews() {
		binding.chbContributorName.isEnabled = true
		binding.etCost.isEnabled = binding.contributor!!.chippedIn
	}
}