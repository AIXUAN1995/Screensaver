package com.ax.screensaver.utils

import android.content.Context
import android.content.SharedPreferences

object SPUtils {

    public const val SP_SCREENSAVER = "screensaver"
    public const val KEY_TEXT_COLOR = "key_text_color"
    public const val KEY_BG_COLOR = "key_bg_color"
    public const val KEY_TEXT = "key_text"
    public const val KEY_TEXT_SIZE = "key_text_size"
    public const val KEY_TEXT_SPEED = "key_text_speed"

    public fun getScreensaverSP(context: Context): SharedPreferences {
        return context.getSharedPreferences(SP_SCREENSAVER, Context.MODE_PRIVATE)
    }

}