package com.workmanager.database.dao

import com.workmanager.database.model.Item
import com.workmanager.database.model.Thing
import kotlinx.coroutines.flow.Flow

interface IThingDao {
    fun getThings(): Flow<List<Thing>>
    fun getItems(): Flow<List<Item>>
    fun insertThing(thing: Thing)
    fun insertThings(things: List<Thing>)
    fun insertItem(item : Item)
    fun insertItems(items : List<Item>)
    suspend fun deleteAllThings()
    suspend fun deleteAllItems()

    suspend fun insertThingSlowly(thing:Thing)
    suspend fun insertItemSlowly(item : Item)
}