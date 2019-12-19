package vl.roomies.data.constants

import vl.roomies.R
import vl.roomies.app.RoomiesApp.Companion.appContext

object IntegerConstants {

	val minPasswordLen by lazy { appContext.resources.getInteger(R.integer.min_password_len) }
}