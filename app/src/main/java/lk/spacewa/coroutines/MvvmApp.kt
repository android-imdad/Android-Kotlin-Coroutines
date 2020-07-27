package lk.spacewa.coroutines

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import lk.spacewa.coroutines.utils.AppLogger

/**
 * Created by Imdad on 05/11/20.
 */
@HiltAndroidApp
class MvvmApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppLogger.init()
    }

}