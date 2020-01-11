package vl.roomies.ui.fridge.creation

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
			viewmodel.setSticker(intent.getParcelableExtra(STICKER_KEY)!!)
		}
		binding.viewmodel = viewmodel

		setupVMObservers()
		setupToolbar()
		etStickerText.requestFocus()
	}

	private fun setupVMObservers() {
		viewmodel.closeWindow.observe(this, Observer {
			setResult(Activity.RESULT_OK)
			finish()
		})
		viewmodel.snackError.observe(this, Observer {
			Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
		})
	}

	private fun setupToolbar() {
		toolbar.setNavigationIcon(R.drawable.ic_up_navigation_arrow)
		toolbar.setNavigationOnClickListener { finish() }
		if (isInCreateMode) {
			setupCreateToolbar()
		} else {
			setupEditToolbar()
		}
	}

	private fun setupCreateToolbar() {
		toolbar.title = getString(R.string.label_create_sticker)
		toolbar.inflateMenu(R.menu.create)
		toolbar.menu.findItem(R.id.toolbar_create).setOnMenuItemClickListener {
			viewmodel.onCreateClick()
			true
		}
	}

	private fun setupEditToolbar() {
		toolbar.title = getString(R.string.label_edit_sticker)
		toolbar.inflateMenu(R.menu.save)
		toolbar.menu.findItem(R.id.toolbar_save).setOnMenuItemClickListener {
			viewmodel.onSaveClick()
			true
		}
	}

	companion object {

		fun startForResultCreate(fragment: Fragment, requestCode: Int) {
			val intent = Intent(fragment.activity!!, CreateEditStickerActivity::class.java)
			intent.putExtra(STICKER_ACTIVITY_MODE_KEY, Mode.STICKER_CREATE)
			fragment.startActivityForResult(intent, requestCode)
		}

		fun startForResultEdit(fragment: Fragment, requestCode: Int, sticker: Sticker) {
			val intent = Intent(fragment.activity!!, CreateEditStickerActivity::class.java)
			intent.putExtra(STICKER_ACTIVITY_MODE_KEY, Mode.STICKER_EDIT)
			intent.putExtra(STICKER_KEY, sticker)
			fragment.startActivityForResult(intent, requestCode)
		}
	}
}
