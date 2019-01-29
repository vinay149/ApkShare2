package com.myapp.vinay.apkshare.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.myapp.vinay.apkshare.model.AppInfo
import javax.sql.DataSource

@Dao
abstract class  ApkDetailsDao {

    @Query("SELECT * FROM ApkDetails")
    abstract fun  getAllApkDetails():LiveData<List<ApkDetails>>
    @Insert
    abstract fun insert(appInfo: ApkDetails)

    @Delete
    abstract fun delete(appInfo: ApkDetails)

    @Query("DELETE FROM apkdetails")
    abstract fun deleteAll()


}