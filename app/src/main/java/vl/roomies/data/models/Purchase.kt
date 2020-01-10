package vl.roomies.data.models

data class Purchase(var user: User,
					var title: String,
					var description: String,
					var cost: Double,
					var contributors: List<Contributor>)