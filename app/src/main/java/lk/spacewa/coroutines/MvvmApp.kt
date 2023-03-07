package lk.spacewa.coroutines

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import dagger.hilt.android.HiltAndroidApp
import lk.spacewa.coroutines.data.RefreshStarwarsDataWork
import lk.spacewa.coroutines.utils.AppLogger
import java.util.concurrent.TimeUnit
import javax.inject.Inject




/**
 * Created by Imdad on 05/11/20.
 */
@HiltAndroidApp
class MvvmApp : Application() {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        AppLogger.init()
        setupWorkManagerJob()
    }

    /**
     * Setup WorkManager background job to 'fetch' new starwars data daily.
     */
    private fun setupWorkManagerJob() {
        // initialize WorkManager with a Factory
        val workManagerConfiguration = Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()
        WorkManager.initialize(this, workManagerConfiguration)

        // Use constraints to require the work only run when the device is charging and the
        // network is unmetered
        val constraints = Constraints.Builder()
                .setRequiresCharging(false)
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build()

        // Specify that the work should attempt to run every day
        val work = PeriodicWorkRequestBuilder<RefreshStarwarsDataWork>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()

        // Enqueue it work WorkManager, keeping any previously scheduled jobs for the same
        // work.
        WorkManager.getInstance(this)
                .enqueueUniquePeriodicWork(RefreshStarwarsDataWork::class.java.name, ExistingPeriodicWorkPolicy.KEEP, work)
    }
}