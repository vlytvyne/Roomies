package vl.roomies.ui.fridge

import androidx.lifecycle.ViewModelProviders
import com.floctopus.ui.common.BasicVM
import vl.roomies.R
import vl.roomies.data.models.Sticker
import vl.roomies.data.source.FirebaseRepository
import vl.roomies.ui.common.MutableActionLiveData

class FridgeVM: BasicVM() {

	val setStickersAction = MutableActionLiveData<List<Sticker>>()
	private var pinnedSticker: Sticker? = null

	init {
		refreshStickers()
	}

	fun refreshStickers() {
		startLoading()

		FirebaseRepository.getAllStickers()
			.addOnSuccessListener { it ->
				val stickers = it.toObjects(Sticker::class.java)
				pinnedSticker = stickers.find { sticker -> sticker.isPinned }
				if (pinnedSticker != null) {
					stickers.remove(pinnedSticker)
					stickers.add(0, pinnedSticker)
				}
				setStickersAction.fire(stickers)
			}
			.addOnFailureListener { handleFetchStickersError(it) }
			.addOnCompleteListener { stopLoading() }
	}

	private fun handleFetchStickersError(throwable: Throwable) {
		showSnackError(R.string.error_unknown)
	}

	fun deleteSticker(sticker: Sticker) {
		FirebaseRepository.deleteSticker(sticker)
	}

	fun pinStickerAtTop(sticker: Sticker) {
		pinnedSticker?.let { unpinStickerAtTop(it) }
		FirebaseRepository.editSticker(sticker.apply { isPinned = true })
		pinnedSticker = sticker
	}

	fun unpinStickerAtTop(sticker: Sticker) {
		FirebaseRepository.editSticker(sticker.apply { isPinned = false })
	}

	companion object {

		fun create(fragment: FridgeFragment) =
			ViewModelProviders.of(fragment)[FridgeVM::class.java]
	}
}