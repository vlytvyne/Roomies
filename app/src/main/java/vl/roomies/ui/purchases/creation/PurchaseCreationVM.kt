package vl.roomies.ui.purchases.creation

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.floctopus.ui.common.BasicVM
import com.google.firebase.Timestamp
import timber.log.Timber
import vl.roomies.R
import vl.roomies.data.models.Contributor
import vl.roomies.data.models.Purchase
import vl.roomies.data.models.User
import vl.roomies.data.source.FirebaseRepository
import vl.roomies.data.source.currentUser
import vl.roomies.ui.common.MutableActionLiveData
import vl.roomies.utils.*

class PurchaseCreationVM: BasicVM() {

	//2way db
	val title = MutableLiveData<String>("")
	val description = MutableLiveData<String>("")
	val cost = MutableLiveData<String>("")

	val setUsersToContributorsAction = MutableActionLiveData<List<User>>()

	val titleError = MutableLiveData<@StringRes Int?>()
	val costError = MutableLiveData<@StringRes Int?>()

	private val titleValidator = TextInputValidator(title, titleError)
	private val costValidator = NumberInputValidator(cost, costError).apply {
		minValue = 0.01
		minValueError = R.string.error_cant_be_0
	}
	private val validators = listOf(titleValidator, costValidator)

	val purchaseCostDouble
		get() = if(cost.value!!.isBlank()) 0.0 else cost.value!!.toDouble()

	init {
		setupUsers()
		validators.subscribeAll()
	}

	private fun setupUsers() {
		FirebaseRepository.getAllUsers()
			.addOnSuccessListener {
				val users = it.toObjects(User::class.java)
				setUsersToContributorsAction.fire(users)
			}
	}

	fun onCreateClick(contributors: List<Contributor>) {
		if (!validators.isAllValid) {
			return
		}
		if (contributors.size == 0) {
			showSnackError(R.string.error_should_have_at_least_1_contributor)
			return
		}
		startLoading()
		disableInput()
		FirebaseRepository.createPurchase(Purchase(currentUser, title.value!!, description.value!!, purchaseCostDouble, contributors, Timestamp.now()))
			.addOnSuccessListener {
				stopLoading()
				enableInput()
			}
			.addOnSuccessListener { closeWindow() }
			.addOnFailureListener { handleCreationException(it) }
	}

	private fun handleCreationException(throwable: Throwable) {
		Timber.d(throwable)
	}

	override fun onCleared() {
		super.onCleared()
		validators.unsubscribeAll()
	}

	companion object {

		fun create(activity: PurchaseCreationActivity) =
			ViewModelProviders.of(activity)[PurchaseCreationVM::class.java]
	}
}