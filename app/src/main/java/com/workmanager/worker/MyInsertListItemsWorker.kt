package com.workmanager.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.workmanager.database.dao.IThingDao
import com.workmanager.database.model.Item
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlin.random.Random


@HiltWorker
class MyInsertListItemsWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val iThingDao: IThingDao,
) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        return try {
            val items = ItemHelper.getRandomItems()
            items.forEach {
                iThingDao.insertItem(it)
                Log.i("TAG", "Item inserted " + it)
            }

            Result.success()
        } catch (ex: Exception) {
            Log.i("TAG", "that is why you fail")
            Log.i("TAG", ex.toString())
            Result.failure()
        }
    }
}

@HiltWorker
class MySlowlyInsertListItemsWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val iThingDao: IThingDao,
) :
    CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            val items = ItemHelper.getRandomItems()
            items.forEach {
                iThingDao.insertItemSlowly(it)
                Log.i("TAG", "Item inserted " + it)
            }

            Result.success()
        } catch (ex: Exception) {
            Log.i("TAG", "that is why you fail")
            Log.i("TAG", ex.toString())
            Result.failure()
        }
    }
}

object ItemHelper{

    fun getRandomItems() : List<Item>
    {
        return (0..100).map {
            Item(it, it.toString())
        }
    }
}