package vl.roomies.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*
import vl.roomies.R
import vl.roomies.ui.fridge.FridgeFragment
import vl.roomies.ui.profile.ProfileFragment
import vl.roomies.ui.purchases.PurchasesFragment

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

	private val purchasesFragment = PurchasesFragment.newInstance()
	private val fridgeFragment = FridgeFragment.newInstance()
	private val profileFragment = ProfileFragment.newInstance()
	private val fragments = listOf(purchasesFragment, fridgeFragment, profileFragment)

	private var currentFragment: Fragment? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_home)

		bottomNavigation.setOnNavigationItemSelectedListener(this)
		bottomNavigation.selectedItemId = R.id.bottom_nav_fridge
		setupFragments()
	}

	override fun onBackPressed() {
		finishAffinity()
	}

	override fun onNavigationItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			R.id.bottom_nav_purchases -> setFragment(purchasesFragment)
			R.id.bottom_nav_fridge -> setFragment(fridgeFragment)
			R.id.bottom_nav_profile -> setFragment(profileFragment)
		}
		return true
	}

	private fun setFragment(fragment: Fragment) {
		val fragmentTransaction = supportFragmentManager.beginTransaction()
		when (fragment) {
			is ProfileFragment -> fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
			is PurchasesFragment -> fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
			is FridgeFragment ->
				when (currentFragment) {
					is PurchasesFragment -> fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
					is ProfileFragment -> fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
				}
		}
		currentFragment?.let { fragmentTransaction.hide(it) }
		fragmentTransaction.show(fragment)
		fragmentTransaction.commit()
		currentFragment = fragment
	}

	private fun setupFragments() {
		val fragmentTransaction = supportFragmentManager.beginTransaction()
		fragments.forEach {
			fragment ->
			fragmentTransaction.add(R.id.frame, fragment)
			fragmentTransaction.hide(fragment)
		}
		fragmentTransaction.commitNow()
	}

	companion object {
		 fun start(activity: AppCompatActivity) {
			 val intent = Intent(activity, HomeActivity::class.java)
			 intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
			 activity.startActivity(intent)
		 }
	}
}
