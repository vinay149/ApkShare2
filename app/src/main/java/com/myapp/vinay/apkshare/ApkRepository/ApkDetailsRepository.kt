package com.myapp.vinay.apkshare.ApkRepository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.os.AsyncTask
import android.util.Log
import com.google.android.gms.internal.phenotype.zzh.init
import com.myapp.vinay.apkshare.database.ApkDetails
import com.myapp.vinay.apkshare.database.ApkDetailsDao
import com.myapp.vinay.apkshare.database.ApkDetailsDataBaseBuilder

class ApkDetailsRepository(var application: Application) {
    var apkDetails :LiveData<List<ApkDetails>>?=null
    var apkDoa:ApkDetailsDao?=null;
    init {
        apkDoa=ApkDetailsDataBaseBuilder.getmInstanceDataBase(application).dataBaseUser.ApkDetailsDao()
        apkDetails= apkDoa!!.getAllApkDetails()

    }
   fun getApkDetail():LiveData<List<ApkDetails>>?
   {
       Log.d("vinayhere123","here"+apkDetails!!.value)
       return apkDetails
   }
    fun insert(apkDetails: ApkDetails) {
        var insertAsynkTask:insertAsynkTask = insertAsynkTask(this!!.apkDoa!!);
        insertAsynkTask.execute(apkDetails);
    }
    fun delete (apkDetails: ApkDetails){
        var deleteAsynkTask:deleteAsynkTask = deleteAsynkTask(this!!.apkDoa!!)
        deleteAsynkTask.execute(apkDetails)
    }
    fun deleteAll()
    {
        var deleteall:deleteAllAsynkTask = deleteAllAsynkTask(this!!.apkDoa!!)
        deleteall.execute()
    }
    class deleteAllAsynkTask(apkDao: ApkDetailsDao):AsyncTask<Void ,Void,Void>(){
        var apkDao:ApkDetailsDao?=null
        init {
            this.apkDao=apkDao
        }
        override fun doInBackground(vararg params: Void): Void? {
          apkDao!!.deleteAll()
            return null
        }
    }
    class  insertAsynkTask(apkDao: ApkDetailsDao) : AsyncTask<ApkDetails, Void, Void>() {
        var apkDao:ApkDetailsDao?=null
        init {
            this.apkDao=apkDao
        }
        override fun doInBackground(vararg params: ApkDetails): Void? {
            Log.d("VinayHere123","Here"+params[0].packageName);
            apkDao!!.insert(params[0])
            return null

        }

    }
    class deleteAsynkTask(apkDao: ApkDetailsDao) :AsyncTask<ApkDetails,Void,Void>()
    {
        var apkDao:ApkDetailsDao?=null
        init {
            this.apkDao=apkDao
        }
        override fun doInBackground(vararg params: ApkDetails): Void? {
           apkDao!!.delete(params[0])
            return null
        }

    }

}