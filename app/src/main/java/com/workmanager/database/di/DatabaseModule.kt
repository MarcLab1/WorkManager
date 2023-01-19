package com.workmanager.database.di

import android.app.Application
import androidx.room.Room
import com.workmanager.database.dao.IThingDao
import com.workmanager.database.dao.ThingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideThingDatabase(application: Application): ThingDatabase {
        return Room.databaseBuilder(application, ThingDatabase::class.java, "thing_db").build()
    }

    @Singleton
    @Provides
    fun provideThingDao(thingDatabase: ThingDatabase): IThingDao {
        return thingDatabase.thingDao()
    }
}