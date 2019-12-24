package vl.roomies.ui.fridge.creation

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_create_edit_sticker.*
import vl.roomies.R
import vl.roomies.data.models.Sticker
import vl.roomies.databinding.ActivityCreateEditStickerBinding

enum class Mode {
	STICKER_CREATE,
	STICKER_EDIT
}

private const val STICKER_ACTIVITY_MODE_KEY = "stickerActivityMode"
private const val STICKER_KEY = "stickerKey"

class CreateEditStickerActivity : AppCompatActivity() {

	private lateinit var viewmodel: CreateEditStickerVM
	private lateinit var binding: ActivityCreateEditStickerBinding

	private var isInCreateMode: Boolean = true

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		isInCreateMode = intent.getSerializableExtra(STICKER_ACTIVITY_MODE_KEY) == Mode.STICKER_CREATE

		binding = DataBindingUtil.setContentView(this, R.layout.activity_create_edit_sticker)
		binding.lifecycleOwner = this
		viewmodel = CreateEditStickerVM.create(this)
		if (!isInCreateMode) {
			viewmodel.setStickerText(intent.getParcelableExtra<Sticker>(STICKER_KEY)!!.text)
		}
		binding.viewmodel = viewmodel

		setupVMObservers()
		setupToolbar()
		etStickerText.requestFocus()
	}

	private fun setupVMObservers() {
		viewmodel.closeWindow.observe(this, Observer {
			finish()
		})
		viewmodel.snackError.observe(this, Observer {
			Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
		})
	}

	private fun setupToolbar() {
		toolbar.title = getString(R.string.label_create_sticker)
		toolbar.setNavigationIcon(R.drawable.ic_up_navigation_arrow)
		toolbar.setNavigationOnClickListener { finish() }
		if (isInCreateMode) {
			toolbar.inflateMenu(R.menu.create)
			toolbar.menu.findItem(R.id.toolbar_create).setOnMenuItemClickListener {
				viewmodel.onCreateClick()
				true
			}
		} else {
			toolbar.inflateMenu(R.menu.save)
			toolbar.menu.findItem(R.id.toolbar_save).setOnMenuItemClickListener {
//				viewmodel.onSaveClick()
				true
			}
		}
	}

	companion object {

		fun start(activity: Activity, mode: Mode, sticker: Sticker? = null) {
			val intent = Intent(activity, CreateEditStickerActivity::class.java)
			intent.putExtra(STICKER_ACTIVITY_MODE_KEY, mode)
			intent.putExtra(STICKER_KEY, sticker)
			activity.startActivity(intent)
		}
	}
}
