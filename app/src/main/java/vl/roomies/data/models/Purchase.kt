package vl.roomies.data.models

data class Purchase(var userId: Int,
					var title: String,
					var description: String,
					var total: Int,
					var contributors: List<Contributor>)