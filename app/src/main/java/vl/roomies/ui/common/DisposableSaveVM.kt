package vl.roomies.ui.common

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

open class DisposableSaveVM: ViewModel() {

	protected val disposables = CompositeDisposable()

	override fun onCleared() {
		disposables.clear()
		super.onCleared()
	}

	operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
		this.add(disposable)
	}

}