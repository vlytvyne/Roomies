package vl.roomies.ui.purchases.your_purchases

import androidx.lifecycle.ViewModelProviders
import com.floctopus.ui.common.BasicVM
import timber.log.Timber
import vl.roomies.data.models.Purchase
import vl.roomies.data.source.FirebaseRepository
import vl.roomies.ui.common.MutableActionLiveData

class YourPurchasesVM: BasicVM() {

	val setPurchasesAction = MutableActionLiveData<List<Purchase>>()

	init {
		refreshPurchases()
	}

	fun refreshPurchases() {
		startLoading()

		FirebaseRepository.getYourPurchases()
			.addOnCompleteListener { stopLoading() }
			.addOnSuccessListener { setPurchasesAction.fire(it.toObjects(Purchase::class.java)) }
			.addOnFailureListener { handleFetchPurchasesError(it) }
	}

	private fun handleFetchPurchasesError(throwable: Throwable) {
		Timber.d(throwable)
	}

	fun deletePurchase(purchase: Purchase) {
		FirebaseRepository.deletePurchase(purchase)
	}

	companion object {

		fun create(fragment: YourPurchasesFragment) =
			ViewModelProviders.of(fragment)[YourPurchasesVM::class.java]
	}
}