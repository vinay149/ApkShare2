package com.myapp.vinay.apkshare.database

import android.arch.persistence.room.Room
import android.content.Context

class ApkDetailsDataBaseBuilder(private val context: Context) {
    val dataBaseUser: ApkDetailsDatabase

    init {
        dataBaseUser = Room.databaseBuilder(context, ApkDetailsDatabase::class.java!!, "ApkData")
                .fallbackToDestructiveMigration()
                .build()
    }

    companion object {
        private var mInstanceDataBase: ApkDetailsDataBaseBuilder? = null
        fun getmInstanceDataBase(context: Context): ApkDetailsDataBaseBuilder {
            if (mInstanceDataBase == null) {
                mInstanceDataBase = ApkDetailsDataBaseBuilder(context)
            }
            return mInstanceDataBase as ApkDetailsDataBaseBuilder
        }
    }
}