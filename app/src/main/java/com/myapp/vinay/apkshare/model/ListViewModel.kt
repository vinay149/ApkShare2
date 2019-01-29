package com.myapp.vinay.apkshare.model

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.myapp.vinay.apkshare.ApkRepository.ApkDetailsRepository
import com.myapp.vinay.apkshare.database.ApkDetails

class ListViewModel(application: Application) : AndroidViewModel(application) {

    var apkDetailsRepository:ApkDetailsRepository?=null
    var liveData:LiveData<List<ApkDetails>>?=null
    init {
        liveData=MutableLiveData()
        apkDetailsRepository = ApkDetailsRepository(application)
       liveData = apkDetailsRepository!!.getApkDetail()
        Log.d("vinayhere123","Here"+liveData!!.value)
    }
    fun insert(apkDetails: ApkDetails){
        apkDetailsRepository!!.insert(apkDetails)
    }
    fun delete(apkDetails: ApkDetails){
        apkDetailsRepository!!.delete(apkDetails)
    }
    fun deletAll()
    {
        apkDetailsRepository!!.deleteAll()
    }
}