package com.myapp.vinay.apkshare.service

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.support.v4.app.NotificationCompat
import android.util.Log

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.myapp.vinay.apkshare.R
import kotlinx.android.synthetic.main.activity_main.view.*
import java.lang.reflect.Method
import android.os.Bundle




public class MyMessigingService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage?) {
        Log.d("ServiceHere","Vinay");
        var CHANNEL_ID="ApkShar"
        if(p0!!.data.size>0)
        {
            var mMessage:String?=p0.data.get("message")
            var mMessage1=p0.data.get("key")
            Log.d("VinayHerehere","vinay"+mMessage+mMessage1+p0.data.size);
        }
        var mBuilder=NotificationCompat.Builder(this)
        mBuilder.setSmallIcon(R.mipmap.share)
        mBuilder.setContentTitle(p0.notification!!.title)
        mBuilder.setContentText(p0!!.notification!!.body)
        var mNotificationManager:NotificationManager?= getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        mNotificationManager!!.notify(0,mBuilder.build())
    }

}