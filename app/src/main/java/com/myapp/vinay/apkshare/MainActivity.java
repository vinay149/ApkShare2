package com.myapp.vinay.apkshare;

import android.content.*;
import android.content.pm.*;
import android.graphics.drawable.*;
import android.net.*;
import android.os.*;
import android.support.v4.content.*;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.util.*;
import android.view.*;
import android.widget.*;

import com.airbnb.lottie.*;
import com.myapp.vinay.apkshare.Adapter.*;
import com.myapp.vinay.apkshare.model.*;

import java.io.*;
import java.util.*;


public class MainActivity extends AppCompatActivity {
    private ArrayList<AppInfo> mAppList=null;
    private RecyclerView mRecylerView=null;
    private RecyclerView.LayoutManager mLayoutManager=null;
    private LinearLayoutManager mLinearLayoutManager=null;
    private CustomAdaper mCustomAdaper=null;
    private ProgressBar mProgressBar=null;
    private LottieAnimationView animationView;
    private LottieAnimationView animationView1;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        animationView=findViewById (R.id.animation_view);
         new loadApkList().execute ();
//         Handler handler=new Handler();
//         handler.postDelayed (new Runnable () {
//             @Override
//             public void run () {
//
//             }
//         },5000);
    }
    private boolean isSystemPackage(PackageInfo packageInfo) {
        return ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }
    
    @Override
    protected void onPause () {
        super.onPause ();
       
    }
    
    @Override
    protected void onRestart () {
        super.onRestart ();
        
    }
    
    public class loadApkList extends AsyncTask<Void,Void,Void>{
    
        @Override
        protected void onPreExecute () {
            super.onPreExecute ();
            animationView.playAnimation ();
            animationView.setVisibility (View.VISIBLE);
        }
    
        @Override
        protected Void doInBackground (Void... voids) {
            mRecylerView=findViewById (R.id.recyclerView);
            mLayoutManager=new LinearLayoutManager (getApplicationContext ());
            mAppList=new ArrayList();
            mRecylerView.setLayoutManager (mLayoutManager);
            List<PackageInfo> mList=getPackageManager ().getInstalledPackages (0);
            for(int i=0;i<mList.size ();i++)
            {
                AppInfo appInfo=new AppInfo ();
                PackageInfo mPackageInfo=mList.get (i);
                String mAppInfo=mPackageInfo.applicationInfo.loadLabel (getPackageManager ()).toString ();
                String mPackName=mPackageInfo.packageName;
                try {
                    ApplicationInfo mApplicationInfo=getPackageManager ().getApplicationInfo (mPackName,0);
                    String mApk=mApplicationInfo.publicSourceDir;
                    Drawable mAppIcon=getApplicationContext ().getPackageManager ().getApplicationIcon (mPackName);
                    Log.d("VinayHere",mApplicationInfo.icon+"Apk"+mApk);
                    if(!isSystemPackage (mPackageInfo)){
                        appInfo.setPackInfo (mPackName);
                        appInfo.setApkPath (mApk);
                        appInfo.setAppInfo (mAppInfo);
                        appInfo.setApkIcon (mAppIcon);
                        mAppList.add (appInfo);}
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace ();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute (Void aVoid) {
            animationView.setVisibility (View.GONE);
            animationView.cancelAnimation ();
            if(mRecylerView!=null) {
                mRecylerView.setAdapter (new CustomAdaper (mAppList, new CustomAdaper.OnItemClickListener () {
                    @Override
                    public void onItemClick (AppInfo item) {
                        File imageFile=new File (item.getApkPath ());
                        Log.d("VinayHere","hereapk"+getFilesDir ()+item.getApkPath ());
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        Uri uri=null;
                        intent.setType("*/*");
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                            uri=Uri.fromFile(imageFile);
                        }
                        else
                        {
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            uri = FileProvider.getUriForFile(getApplicationContext(),  ".provider",
                                    imageFile);
                        }
                        intent.putExtra(Intent.EXTRA_STREAM,uri);
                        startActivity(Intent.createChooser(intent, "Share app via"));
                    }
                }));
            }
        }
    }
}
