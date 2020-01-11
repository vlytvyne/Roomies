package vl.roomies.data.models

import com.google.firebase.Timestamp
import vl.roomies.data.source.currentUser

data class Purchase(var buyer: User,
                    var title: String,
                    var description: String,
                    var cost: String,
                    var contributors: List<Contributor>,
                    var timeCreated: Timestamp) {

    constructor(): this(currentUser, "", "", "0", listOf(), Timestamp.now())
}