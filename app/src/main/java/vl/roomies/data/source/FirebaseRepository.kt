package vl.roomies.data.source

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import timber.log.Timber
import vl.roomies.data.models.Purchase
import vl.roomies.data.models.Sticker
import vl.roomies.data.models.User

private const val COLL_USERS = "users"
private const val COLL_STICKERS = "stickers"
private const val COLL_PURCHASES = "purchases"

object FirebaseRepository {

	private val firebaseAuth = FirebaseAuth.getInstance()
	private val database = FirebaseFirestore.getInstance()

	private val usersCollection = database.collection(COLL_USERS)
	private val stickersCollection = database.collection(COLL_STICKERS)
	private val purchasesCollection = database.collection(COLL_PURCHASES)

	fun isUserLoggedIn() = firebaseAuth.currentUser != null

	fun logIn(email: String, password: String) =
		firebaseAuth.signInWithEmailAndPassword(email, password)

	fun signUp(email: String, password: String) =
		firebaseAuth.createUserWithEmailAndPassword(email, password)

	fun logOut() =
		firebaseAuth.signOut()

	fun updateUserInfo(user: User) =
		usersCollection.document(firebaseAuth.uid!!).set(user, SetOptions.merge())

	fun getCurrentUserInfo() =
		usersCollection.document(firebaseAuth.uid!!).get()

	fun createSticker(sticker: Sticker) =
		stickersCollection.add(sticker)

	fun getAllStickers() =
		stickersCollection.orderBy("timeCreated", Query.Direction.DESCENDING).get()

	fun deleteSticker(sticker: Sticker) =
		stickersCollection.document(sticker.id!!).delete()

	fun editSticker(sticker: Sticker) =
		stickersCollection.document(sticker.id!!).set(sticker)

	fun getAllUsers() =
		usersCollection.get()

	fun createPurchase(purchase: Purchase) =
		purchasesCollection.add(purchase)
}