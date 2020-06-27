package com.ax.screensaver

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.ax.screensaver.utils.Const.DEFAULT_TEXT_SIZE
import com.ax.screensaver.utils.Const.DEFAULT_TEXT_SPEED
import com.ax.screensaver.utils.Const.REQUEST_CODE_TO_COLOR_PICKER
import com.ax.screensaver.utils.SPUtils
import com.ax.screensaver.utils.SPUtils.KEY_TEXT
import com.ax.screensaver.utils.SPUtils.KEY_TEXT_SIZE
import com.ax.screensaver.utils.SPUtils.KEY_TEXT_SPEED
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SPUtils.getScreensaverSP(this).also {
            textEt.setText(it.getString(KEY_TEXT, "我多想回到那个夏天，蝉鸣在田边吹过眼睫"))
            textSizeEt.setText(it.getInt(KEY_TEXT_SIZE, DEFAULT_TEXT_SIZE).toString())
            textSpeedEt.setText(it.getInt(KEY_TEXT_SPEED, DEFAULT_TEXT_SPEED).toString())
        }
        previewBtn.setOnClickListener {
            // 保存文字、字号
            SPUtils.getScreensaverSP(this).edit().also {
                it.putString(KEY_TEXT, textEt.text.toString())
                it.putInt(KEY_TEXT_SIZE,
                    textSizeEt.text.toString()
                        .let { text -> if (TextUtils.isEmpty(text)) "$DEFAULT_TEXT_SIZE" else text }.toInt()
                )
                it.putInt(KEY_TEXT_SPEED,
                    textSpeedEt.text.toString()
                        .let { speed -> if (TextUtils.isEmpty(speed)) "$DEFAULT_TEXT_SPEED" else speed }.toInt()
                )
            }.apply()

            intent = Intent(this, ScreensaverActivity::class.java)
            startActivity(intent)
        }
        textColorBtn.setOnClickListener {
            intent = Intent(this, ColorPickerActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_TO_COLOR_PICKER)
        }
    }
}
