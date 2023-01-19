package com.workmanager.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.workmanager.database.dao.IThingDao
import com.workmanager.ui.ThingHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlin.random.Random

@HiltWorker
class MyInsertThingWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val iThingDao: IThingDao,
) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        return try {
            val thing = ThingHelper.getRandomThings()[Random.nextInt(20)]
            iThingDao.insertThing(thing)
            Result.success()
        } catch (ex: Exception) {
            Log.i("TAG", "that is why you fail")
            Log.i("TAG", ex.toString())
            Result.failure()
        }
    }
}