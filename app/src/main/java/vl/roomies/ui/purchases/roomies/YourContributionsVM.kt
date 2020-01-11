package vl.roomies.ui.purchases.roomies

import androidx.lifecycle.ViewModelProviders
import com.floctopus.ui.common.BasicVM
import timber.log.Timber
import vl.roomies.data.models.Purchase
import vl.roomies.data.source.FirebaseRepository
import vl.roomies.ui.common.MutableActionLiveData

class YourContributionsVM: BasicVM() {

	val setPurchasesAction = MutableActionLiveData<List<Purchase>>()

	init {
		refreshPurchases()
	}

	fun refreshPurchases() {
		startLoading()

		FirebaseRepository.getYourContributions()
			.addOnCompleteListener { stopLoading() }
			.addOnSuccessListener { setPurchasesAction.fire(it.toObjects(Purchase::class.java)) }
			.addOnFailureListener { handleFetchPurchasesError(it) }
	}

	private fun handleFetchPurchasesError(throwable: Throwable) {
		Timber.d(throwable)
	}

	companion object {

		fun create(fragment: RoomiesPurchasesFragment) =
			ViewModelProviders.of(fragment)[YourContributionsVM::class.java]
	}
}