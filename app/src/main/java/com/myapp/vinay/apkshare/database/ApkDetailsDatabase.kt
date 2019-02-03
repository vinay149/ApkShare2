package com.myapp.vinay.apkshare.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(ApkDetails::class),version = 3)
abstract class ApkDetailsDatabase : RoomDatabase() {
    abstract fun ApkDetailsDao() :ApkDetailsDao
}