package com.myapp.vinay.apkshare.model;

import android.graphics.drawable.*;

public class  AppInfo  {
    
    private String appInfo;
    private String packInfo;
    private String apkPath;
    private Drawable apkIcon;
    public String getApkPath () {
        return apkPath;
    }
    
    public String getAppInfo () {
        return appInfo;
    }
    
    public String getPackInfo () {
        return packInfo;
    }
    
    public void setApkPath (String apkPath) {
        this.apkPath = apkPath;
    }
    
    public void setAppInfo (String appInfo) {
        this.appInfo = appInfo;
    }
    
    public void setPackInfo (String packInfo) {
        this.packInfo = packInfo;
    }
    
    public Drawable getApkIcon () {
        return apkIcon;
    }
    
    public void setApkIcon (Drawable apkIcon) {
        this.apkIcon = apkIcon;
    }
}
