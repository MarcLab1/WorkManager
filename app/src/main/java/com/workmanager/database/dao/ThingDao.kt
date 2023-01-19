package com.workmanager.database.dao

import androidx.room.*
import com.workmanager.database.model.Item
import com.workmanager.database.model.Thing
import kotlinx.coroutines.flow.Flow

@Dao
interface ThingDao : IThingDao {

    @Query("SELECT * FROM Thing")
    override fun getThings(): Flow<List<Thing>>

    @Query("SELECT * FROM Item")
    override fun getItems(): Flow<List<Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insertThing(thing: Thing)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insertThings(things: List<Thing>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insertItem(item : Item)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insertItems(items: List<Item>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertThingSlowly(thing: Thing)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insertItemSlowly(item: Item)

    @Query("Delete from Thing")
    override suspend fun deleteAllThings()

    @Query("Delete from Item")
    override suspend fun deleteAllItems()
}