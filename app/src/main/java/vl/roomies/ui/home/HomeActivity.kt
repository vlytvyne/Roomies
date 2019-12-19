package vl.roomies.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*
import vl.roomies.R
import vl.roomies.data.models.User
import vl.roomies.data.source.FirebaseRepository
import vl.roomies.data.source.currentUser
import vl.roomies.ui.profile.ProfileFragment

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_home)

		bottomNavigation.setOnNavigationItemSelectedListener(this)
		bottomNavigation.selectedItemId = R.id.bottom_nav_profile

		FirebaseRepository.getCurrentUserInfo()
			.addOnSuccessListener {
				currentUser = it.toObject(User::class.java) as User
			}
	}

	override fun onBackPressed() {
		finishAffinity()
	}

	override fun onNavigationItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			bottomNavigation.selectedItemId -> Unit
			R.id.bottom_nav_purchases -> Unit
			R.id.bottom_nav_fridge -> setFragment(ProfileFragment.newInstance())
			R.id.bottom_nav_profile -> setFragment(ProfileFragment.newInstance())
		}
		return true
	}

	private fun setFragment(fragment: Fragment) {
		supportFragmentManager.beginTransaction()
			.replace(R.id.frame, fragment)
			.commit()
	}

	companion object {
		 fun start(activity: AppCompatActivity) {
			 val intent = Intent(activity, HomeActivity::class.java)
			 intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
			 activity.startActivity(intent)
		 }
	}
}
