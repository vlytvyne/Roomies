package vl.roomies.ui.fridge.creation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.floctopus.ui.common.BasicVM
import vl.roomies.R
import vl.roomies.data.models.Sticker
import vl.roomies.data.source.FirebaseRepository

class CreateEditStickerVM: BasicVM() {

	//2way db
	val stickerText = MutableLiveData<String>("")

	private lateinit var sticker: Sticker

	fun setSticker(sticker: Sticker) {
		this.sticker = sticker
		this.stickerText.value = sticker.text
	}

	fun onCreateClick() {
		if (stickerText.value.isNullOrBlank()) {
			showSnackError(R.string.error_field_cant_be_blank)
		} else {
			createSticker()
		}
	}

	private fun createSticker() {
		startLoading()
		disableInput()

		FirebaseRepository.createSticker(Sticker(stickerText.value.toString()))
			.addOnSuccessListener { closeWindow() }
			.addOnFailureListener { handleCreationError(it) }
			.addOnCompleteListener {
				stopLoading()
				enableInput()
			}
	}

	private fun handleCreationError(throwable: Throwable) {
		showSnackError(R.string.error_unknown)
	}

	fun onSaveClick() {
		if (stickerText.value.isNullOrBlank()) {
			showSnackError(R.string.error_field_cant_be_blank)
		} else {
			saveEditedSticker()
		}
	}

	private fun saveEditedSticker() {
		startLoading()
		disableInput()

		FirebaseRepository.editSticker(sticker.apply { text = stickerText.value!! })
			.addOnSuccessListener { closeWindow() }
			.addOnFailureListener { handleCreationError(it) }
			.addOnCompleteListener {
				stopLoading()
				enableInput()
			}
	}

	companion object {

		fun create(activity: CreateEditStickerActivity) =
			ViewModelProviders.of(activity)[CreateEditStickerVM::class.java]
	}
}