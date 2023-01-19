package com.workmanager.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Thing(@PrimaryKey val id: Int = 0, val name: String = "")
