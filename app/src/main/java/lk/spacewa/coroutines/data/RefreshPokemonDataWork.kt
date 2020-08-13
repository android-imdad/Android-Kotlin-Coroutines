package lk.spacewa.coroutines.data

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.*
import timber.log.Timber

/**
 * Created by Imdad on 8/7/2020.
 */
class RefreshPokemonDataWork @WorkerInject constructor (@Assisted context: Context, @Assisted params: WorkerParameters, private val dataManager: AppDataManager) :
        CoroutineWorker(context, params) {


    /**
     * Refresh the title from the network using [TitleRepository]
     *
     * WorkManager will call this method from a background thread. It may be called even
     * after our app has been terminated by the operating system, in which case [WorkManager] will
     * start just enough to run this [Worker].
     */
    override suspend fun doWork(): Result {
        return try {
            dataManager.getPokemonsAndSaveBlocking()

            Result.success()
        } catch (error: Throwable) {

            Result.failure()
        }
    }

}