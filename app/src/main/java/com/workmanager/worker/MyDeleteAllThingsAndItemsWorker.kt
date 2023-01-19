package com.workmanager.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.workmanager.database.dao.IThingDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay

@HiltWorker
class MyDeleteAllThingsAndItemsWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val iThingDao: IThingDao,
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        try {
            iThingDao.deleteAllThings()
            delay(1000L)
            iThingDao.deleteAllItems()

        } catch (exception: Exception) {
            Log.i("TAG", "we failed in our quest")
            return Result.failure()
        }
        return Result.success()
    }
}