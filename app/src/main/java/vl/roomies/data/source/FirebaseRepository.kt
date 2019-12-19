package vl.roomies.data.source

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import vl.roomies.data.models.User

private const val COLL_USERS = "users"

object FirebaseRepository {

	private val firebaseAuth = FirebaseAuth.getInstance()
	private val database = FirebaseFirestore.getInstance()

	fun isUserLoggedIn() = firebaseAuth.currentUser != null

	fun logIn(email: String, password: String) =
		firebaseAuth.signInWithEmailAndPassword(email, password)

	fun signUp(email: String, password: String) =
		firebaseAuth.createUserWithEmailAndPassword(email, password)

	fun logOut() =
		firebaseAuth.signOut()

	fun updateUserInfo(user: User) =
		database.collection(COLL_USERS).document(firebaseAuth.uid!!).set(user, SetOptions.merge())

	fun getCurrentUserInfo() =
		database.collection(COLL_USERS).document(firebaseAuth.uid!!).get()
}