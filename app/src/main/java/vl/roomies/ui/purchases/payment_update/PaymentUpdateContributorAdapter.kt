package vl.roomies.ui.purchases.payment_update

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vl.roomies.data.models.Contributor
import vl.roomies.databinding.VhPurchasePaymentUpdateContributorBinding
import vl.roomies.utils.CutDigitsAfterDotWatcher

class PaymentUpdateContributorAdapter: RecyclerView.Adapter<VH>() {

	private var contributors = listOf<Contributor>()

	fun setContributors(contributors: List<Contributor>) {
		this.contributors = contributors
		notifyDataSetChanged()
	}

	fun everybodyPaid() {
		contributors.forEach { it.isPaid = true }
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
		val inflater = LayoutInflater.from(parent.context)
		return VH(VhPurchasePaymentUpdateContributorBinding.inflate(inflater, parent, false))
	}

	override fun onBindViewHolder(holder: VH, position: Int) {
		holder.bind(contributors[position])
	}

	override fun getItemCount(): Int = contributors.size
}

class VH(private val binding: VhPurchasePaymentUpdateContributorBinding): RecyclerView.ViewHolder(binding.root) {

	//idk why but OnCheckChangedListener works laggy only after some actions. So I had to do some sex with clickListener
	fun bind(contributor: Contributor) {
		binding.etCost.addTextChangedListener(CutDigitsAfterDotWatcher(2))
		binding.contributor = contributor
		binding.etCost.isEnabled = !contributor.isPaid
		binding.chbContributorName.setOnClickListener {
			binding.etCost.isEnabled = !binding.chbContributorName.isChecked
		}
	}
}