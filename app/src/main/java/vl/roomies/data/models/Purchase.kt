package vl.roomies.data.models

data class Purchase(var userId: Int,
					var title: String,
					var description: String,
					var total: Int,
					var borrowers: List<Borrower>)

data class Borrower(var userId: Int,
					var oweAmount: Int,
					var isPaid: Boolean)