package vl.roomies.ui.purchases.payment_update

import androidx.lifecycle.ViewModelProviders
import com.floctopus.ui.common.BasicVM
import timber.log.Timber
import vl.roomies.R
import vl.roomies.data.models.Purchase
import vl.roomies.data.source.FirebaseRepository

class PaymentUpdateVM: BasicVM() {

	fun onSaveClick(purchase: Purchase) {
		purchase.contributors.forEach { if(it.moneyPart.isBlank() || !it.moneyPart.first().isDigit()) it.moneyPart = "0" }
		startLoading()

		FirebaseRepository.editPurchase(purchase)
			.addOnCompleteListener { stopLoading() }
			.addOnSuccessListener { closeWindow() }
			.addOnFailureListener { handleEditError(it) }
	}

	private fun handleEditError(throwable: Throwable) {
		Timber.d(throwable)
		showSnackError(R.string.error_unknown)
	}

	companion object {

		fun create(activity: PaymentUpdateActivity) =
			ViewModelProviders.of(activity)[PaymentUpdateVM::class.java]
	}
}