package com.ax.screensaver

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.ax.screensaver.utils.Const.DEFAULT_TEXT_SIZE
import com.ax.screensaver.utils.Const.DEFAULT_TEXT_SPEED
import com.ax.screensaver.utils.SPUtils
import com.ax.screensaver.utils.SPUtils.KEY_BG_COLOR
import com.ax.screensaver.utils.SPUtils.KEY_TEXT
import com.ax.screensaver.utils.SPUtils.KEY_TEXT_COLOR
import com.ax.screensaver.utils.SPUtils.KEY_TEXT_SIZE
import com.ax.screensaver.utils.SPUtils.KEY_TEXT_SPEED
import kotlinx.android.synthetic.main.activity_screensaver.*

class ScreensaverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screensaver)
        SPUtils.getScreensaverSP(this).also {
            screensaverLayout.setBackgroundColor(it.getInt(KEY_BG_COLOR, Color.BLACK))
            screensaverTv.setTextColor(it.getInt(KEY_TEXT_COLOR, Color.WHITE))
            screensaverTv.text = it.getString(KEY_TEXT, "").let { text ->
                if (!TextUtils.isEmpty(text)) text else "我多想回到那个夏天，蝉鸣在田边吹过眼睫"
            }
            screensaverTv.textSize = it.getInt(KEY_TEXT_SIZE, DEFAULT_TEXT_SIZE).toFloat()
            screensaverTv.setSpeed(it.getInt(KEY_TEXT_SPEED, DEFAULT_TEXT_SPEED))
        }
    }
}
