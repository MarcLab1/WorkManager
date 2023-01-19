package com.workmanager.ui

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.workmanager.database.model.Thing
import com.workmanager.database.dao.IThingDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

class RandomDependency @Inject constructor() {

    fun doSomething(): Unit {
        Log.i("TAG", "left worker doing something in the background")
    }

    fun doSomethingElse() {
        Log.i("TAG", "right coroutine worker doing something else in the background")
    }
}

@HiltWorker
class MyInsertListThingsWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val iThingDao: IThingDao,
) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        return try {
            val things = ThingHelper.getRandomThings()
            things.forEach {
                iThingDao.insertThing(it)
                Log.i("TAG", ">>Thing inserted" + it)
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
class MySlowlyInsertListThingsWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val iThingDao: IThingDao,
) :
   CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            val things = ThingHelper.getRandomThings()
            things.forEach {
                iThingDao.insertThingSlowly(it)
                Log.i("TAG", ">>Thing inserted" + it)
            }

            Result.success()
        } catch (ex: Exception) {
            Log.i("TAG", "that is why you fail")
            Log.i("TAG", ex.toString())
            Result.failure()
        }
    }
}

object ThingHelper{

    fun getRandomThings() : List<Thing>
    {
        return (0..20).map {
            Thing(it, it.toString())
        }
    }
}

