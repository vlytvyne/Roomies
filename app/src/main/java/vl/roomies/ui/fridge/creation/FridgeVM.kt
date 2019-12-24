package vl.roomies.ui.fridge.creation

import androidx.lifecycle.ViewModelProviders
import com.floctopus.ui.common.BasicVM
import vl.roomies.R
import vl.roomies.data.models.Sticker
import vl.roomies.data.source.FirebaseRepository
import vl.roomies.ui.common.MutableActionLiveData
import vl.roomies.ui.fridge.FridgeFragment

class FridgeVM: BasicVM() {

	val setStickersAction = MutableActionLiveData<List<Sticker>>()

	init {
		fetchStickers()
	}

	private fun fetchStickers() {
		startLoading()

		FirebaseRepository.getAllStickers()
			.addOnSuccessListener { setStickersAction.fire(it.toObjects(Sticker::class.java)) }
			.addOnFailureListener { handleFetchStickersError(it) }
			.addOnCompleteListener { stopLoading() }
	}

	private fun handleFetchStickersError(throwable: Throwable) {
		showSnackError(R.string.error_unknown)
	}

	companion object {

		fun create(fragment: FridgeFragment) =
			ViewModelProviders.of(fragment)[FridgeVM::class.java]
	}
}