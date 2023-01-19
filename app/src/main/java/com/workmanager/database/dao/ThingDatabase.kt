package com.workmanager.database.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.workmanager.database.model.Item
import com.workmanager.database.model.Thing


@Database(entities = [Thing::class, Item::class], version = 1)
abstract class ThingDatabase : RoomDatabase() {

    abstract fun thingDao() : ThingDao
}