package com.ax.screensaver

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.ax.screensaver.utils.SPUtils
import com.ax.screensaver.utils.SPUtils.KEY_BG_COLOR
import com.ax.screensaver.utils.SPUtils.KEY_TEXT_COLOR
import kotlinx.android.synthetic.main.activity_color_picker.*

class ColorPickerActivity : AppCompatActivity() {

    private var spinnerSelectedIndex = 0
    private var textColor = Color.WHITE
    private var bgColor = Color.BLACK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_picker)
        SPUtils.getScreensaverSP(this).also {
            textColor = it.getInt(KEY_TEXT_COLOR, Color.WHITE)
            bgColor = it.getInt(KEY_BG_COLOR, Color.BLACK)
            previewTv.setTextColor(textColor)
            previewTv.setBackgroundColor(bgColor)
        }
        previewTv.setTextColor(picker.color)
        picker.addOpacityBar(opacityBar)
        picker.addSVBar(svBar)
        picker.setOnColorChangedListener {
            when (spinnerSelectedIndex) {
                0 -> textColor = picker.color
                1 -> bgColor = picker.color
            }
            previewTv.setTextColor(textColor)
            previewTv.setBackgroundColor(bgColor)
        }
        confirmBtn.setOnClickListener {
            SPUtils.getScreensaverSP(this).edit().also {
                it.putInt(KEY_TEXT_COLOR, textColor)
                it.putInt(KEY_BG_COLOR, bgColor)
            }.apply()
            finish()
        }
        ArrayAdapter.createFromResource(
                this,
                R.array.part_array,
                android.R.layout.simple_spinner_item
            )
            .also { adapter -> partSpinner.adapter = adapter }
        partSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                    spinnerSelectedIndex = pos
                    when (spinnerSelectedIndex) {
                        0 -> picker.color = textColor
                        1 -> picker.color = bgColor
                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Another interface callback
                }
        }
    }

    companion object {
        private const val TAG = "ColorPickerActivity"
    }
}
