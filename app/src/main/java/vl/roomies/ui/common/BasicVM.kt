package com.floctopus.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vl.roomies.ui.common.ActionLiveData
import vl.roomies.ui.common.DisposableSaveVM
import vl.roomies.ui.common.MutableActionLiveData
import vl.roomies.ui.common.fire

open class BasicVM : DisposableSaveVM() {

	private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
	val isLoading: LiveData<Boolean> = _isLoading

	private val _isNoInternet = MutableLiveData<Boolean>().apply { value = false }
	val isNoInternet: LiveData<Boolean> = _isNoInternet

	private val _isMainContentVisible = MutableLiveData<Boolean>().apply { value = true }
	val isMainContentVisible: LiveData<Boolean> = _isMainContentVisible

	private val _hideKeyboardAction = MutableActionLiveData<Unit>()
	val hideKeyboardAction: ActionLiveData<Unit> = _hideKeyboardAction

	private val _closeWindow = MutableActionLiveData<Unit>()
	val closeWindow: ActionLiveData<Unit> = _closeWindow

	fun startLoading() {
		_isLoading.value = true
	}

	fun stopLoading() {
		_isLoading.value = false
	}

	fun hideKeyboard() {
		_hideKeyboardAction.fire()
	}

	fun showMainContent() {
		_isMainContentVisible.value = true
	}

	fun hideMainContent() {
		_isMainContentVisible.value = false
	}

	fun closeWindow() {
		_closeWindow.fire()
	}
}