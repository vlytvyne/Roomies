package vl.roomies.data.models

import com.google.firebase.Timestamp

data class Purchase(var buyer: User,
                    var title: String,
                    var description: String,
                    var cost: Double,
                    var contributors: List<Contributor>,
                    var timeCreated: Timestamp)