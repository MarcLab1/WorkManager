package com.workmanager.ui

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.workmanager.database.model.Thing
import com.workmanager.database.dao.IThingDao
import com.workmanager.database.model.Item
import com.workmanager.worker.MyDeleteAllThingsAndItemsWorker
import com.workmanager.worker.MyInsertListItemsWorker
import com.workmanager.worker.MyInsertThingWorker
import com.workmanager.worker.MySlowlyInsertListItemsWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val app: Application, val thingDao: IThingDao) :
    ViewModel() {

    val things: MutableState<List<Thing>> = mutableStateOf(emptyList())
    val items: MutableState<List<Item>> = mutableStateOf(emptyList())

    init {
        viewModelScope.launch {
            thingDao.getThings().collectLatest {
                things.value = it
            }
        }
        viewModelScope.launch {
            thingDao.getItems().collectLatest {
                items.value = it
            }
        }
    }

    fun addOneThing() {
        val workRequest = OneTimeWorkRequestBuilder<MyInsertThingWorker>().build()
        WorkManager.getInstance(app).enqueue(workRequest)
    }

    fun addThingPeriodically() {
        //The minimum repeat interval that can be defined is 15 minutes (same as the JobScheduler API).
        val workRequest =
            PeriodicWorkRequestBuilder<MyInsertThingWorker>(15, TimeUnit.MINUTES).build()

        WorkManager.getInstance(app).enqueue(workRequest)
    }

    fun deleteAllThingsAndItems() {
        viewModelScope.launch {
            val workRequest = OneTimeWorkRequestBuilder<MyDeleteAllThingsAndItemsWorker>().build()
            WorkManager.getInstance(app).enqueue(workRequest)
        }
    }

    fun chainRequests() {
        WorkManager.getInstance(app).beginWith(
            OneTimeWorkRequest.from(MyInsertListItemsWorker::class.java)
        ).then(OneTimeWorkRequest.from(MyInsertListThingsWorker::class.java)).enqueue()
    }

    fun chainRequestsSlowly() {
        viewModelScope.launch {
            WorkManager.getInstance(app).beginWith(
                OneTimeWorkRequest.from(MySlowlyInsertListItemsWorker::class.java)
            ).then(OneTimeWorkRequest.from(MySlowlyInsertListThingsWorker::class.java)).enqueue()
        }
    }
    fun chainRequestsOnlyOneSlowly() {
        viewModelScope.launch {
            WorkManager.getInstance(app).beginWith(
                OneTimeWorkRequest.from(MyInsertListItemsWorker::class.java)
            ).then(OneTimeWorkRequest.from(MySlowlyInsertListThingsWorker::class.java)).enqueue()
        }
    }

}