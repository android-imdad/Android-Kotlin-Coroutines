package lk.spacewa.coroutines.utils

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.util.Patterns
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Imdad on 05/11/20.
 */
object CommonUtils {
    @SuppressLint("all")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    val timestamp: String
        get() = SimpleDateFormat(AppConstants.TIMESTAMP_FORMAT, Locale.US).format(Date())

    fun isEmailValid(email: String?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    @Throws(IOException::class)
    fun loadJSONFromAsset(context: Context, jsonFileName: String?): String {
        val manager = context.assets
        val `is` = manager.open(jsonFileName)
        val size = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        return String(buffer, Charsets.UTF_8)
    }

}