package vl.roomies.ui.fridge.creation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.floctopus.ui.common.BasicVM
import vl.roomies.R
import vl.roomies.data.models.Sticker
import vl.roomies.data.source.FirebaseRepository

class CreateEditStickerVM: BasicVM() {

	//2way db
	val text = MutableLiveData<String>("")

	fun setStickerText(text: String) {
		this.text.value = text
	}

	fun onCreateClick() {
		if (text.value.isNullOrBlank()) {
			showSnackError(R.string.error_field_cant_be_blank)
		} else {
			createSticker()
		}
	}

	private fun createSticker() {
		startLoading()
		disableInput()

		FirebaseRepository.createSticker(Sticker(text.value.toString()))
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

	companion object {

		fun create(activity: CreateEditStickerActivity) =
			ViewModelProviders.of(activity)[CreateEditStickerVM::class.java]
	}
}