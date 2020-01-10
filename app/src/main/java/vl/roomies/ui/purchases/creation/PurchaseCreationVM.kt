package vl.roomies.ui.purchases.creation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.floctopus.ui.common.BasicVM
import vl.roomies.data.models.User
import vl.roomies.data.source.FirebaseRepository
import vl.roomies.ui.common.MutableActionLiveData

class PurchaseCreationVM: BasicVM() {

	//2way db
	val title = MutableLiveData<String>("")
	val description = MutableLiveData<String>("")
	val cost = MutableLiveData<String>("0")
	val setUsersToContributorsAction = MutableActionLiveData<List<User>>()

	val purchaseCurrentCost
		get() = if(cost.value!!.isBlank()) 0.0 else cost.value!!.toDouble()

	init {
		setupUsers()
	}

	private fun setupUsers() {
		FirebaseRepository.getAllUsers()
			.addOnSuccessListener {
				val users = it.toObjects(User::class.java)
				setUsersToContributorsAction.fire(users)
			}
	}

	companion object {

		fun create(activity: PurchaseCreationActivity) =
			ViewModelProviders.of(activity)[PurchaseCreationVM::class.java]
	}
}