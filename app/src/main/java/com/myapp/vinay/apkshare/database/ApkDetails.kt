package com.myapp.vinay.apkshare.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable
import java.util.*

@Entity
class ApkDetails :Serializable{

    @NotNull
    @PrimaryKey()
    var packageName:String?=null

    @ColumnInfo(name = "app_name")
    var appName:String?=null

    @ColumnInfo(name ="apk_path")

    var appPath:String?=null

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var appIconDrawable:ByteArray?=null

}