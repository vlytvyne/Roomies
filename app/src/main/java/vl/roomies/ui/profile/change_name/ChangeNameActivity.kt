package vl.roomies.ui.profile.change_name

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_change_name.*
import kotlinx.android.synthetic.main.fragment_profile.toolbar
import vl.roomies.R
import vl.roomies.data.constants.NEW_NAME_KEY
import vl.roomies.data.source.currentUser

class ChangeNameActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_change_name)

		setupToolbar()

		etNewName.setText(currentUser.name)
	}

	private fun setupToolbar() {
		toolbar.title = getString(R.string.label_change_name)
		toolbar.setNavigationIcon(R.drawable.ic_up_navigation_arrow)
		toolbar.setNavigationOnClickListener { finish() }
		toolbar.inflateMenu(R.menu.save)
		toolbar.menu.findItem(R.id.toolbar_save).setOnMenuItemClickListener {
			setResult(Activity.RESULT_OK, Intent().putExtra(NEW_NAME_KEY, etNewName.text))
			finish()
			true
		}
	}

	companion object {

		fun startForResult(fragment: Fragment, requestCode: Int) {
			val intent = Intent(fragment.activity!!, ChangeNameActivity::class.java)
			fragment.startActivityForResult(intent, requestCode)
		}
	}
}
