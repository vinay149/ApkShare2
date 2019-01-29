package com.myapp.vinay.apkshare
import android.content.*
import android.content.pm.*
import android.net.*
import android.os.*
import android.support.v4.content.*
import android.support.v7.widget.*
import android.util.*
import android.view.*
import com.airbnb.lottie.*
import com.myapp.vinay.apkshare.Adapter.*
import com.myapp.vinay.apkshare.model.*

import java.io.*
import java.util.*

import android.app.*
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.SearchView
import com.myapp.vinay.apkshare.database.ApkDetails
import kotlin.collections.ArrayList
import java.lang.Exception
import android.graphics.drawable.BitmapDrawable
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.WindowDecorActionBar

@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener, View.OnClickListener, CustomAdaper.OnItemClickListener ,NoticeDialogFragments.NoticeDialogListener{

    private var mAppList: ArrayList<AppInfo>? = null
    private var mRecylerView: RecyclerView? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    private var animationView: LottieAnimationView? = null
    private var customAdaper: CustomAdaper? = null
    private var listViewModel: ListViewModel? = null
    var mApkClickedItem:AppInfo?=null
    var apkMap:HashMap<String,Int>?=null;
    var mCount:Int?=null
    override fun onDialogPositiveClick(dialog: Dialog,int: Int) {
        if(int==0){
        shareapk(mApkClickedItem!!)
        } else{
            sharePlayStoreLink(mApkClickedItem!!.packInfo)
        }
        dialog.dismiss()
    }
    override fun onDialogNegativeClick(dialog: Dialog) {
        dialog.dismiss()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        animationView = findViewById(R.id.animation_view)
        animationView!!.playAnimation()
        animationView!!.visibility = View.VISIBLE
        apkMap=HashMap();
        mAppList = ArrayList()
        listViewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        listViewModel!!.liveData!!.observe(this, Observer {
            mCount=it!!.size
            for (i in it!!){
                var appInfo:AppInfo=AppInfo();
                appInfo.packInfo = i.packageName
                appInfo.apkPath = "1"
                appInfo.appInfo = i.appName

                if(i!!.appIconDrawable !=null) {

                    val image = BitmapDrawable(resources, BitmapFactory.decodeByteArray(i.appIconDrawable, 0, i.appIconDrawable!!.size))
                    appInfo.apkIcon = image


                }
                if(apkMap!![appInfo.packInfo]!=1) {
                    mAppList!!.add(appInfo)
                    apkMap!![appInfo.packInfo]=1
                }
                mRecylerView = findViewById(R.id.recyclerView)
                mLayoutManager = LinearLayoutManager(applicationContext)
                mRecylerView!!.layoutManager = mLayoutManager
                if (mRecylerView != null) {
                    customAdaper = CustomAdaper(mAppList, this@MainActivity,this)
                    mRecylerView!!.adapter = customAdaper
                    animationView!!.visibility = View.GONE
                    animationView!!.cancelAnimation()
                }

            }
            Log.d("VinayHere1234", "Vinay");
            var apkList:loadApkList?= loadApkList().execute() as loadApkList?
        })
    }

    private fun isSystemPackage(packageInfo: PackageInfo): Boolean {
        return packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }

    override fun onPause() {
        super.onPause()

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchMenuItem = menu.findItem(R.id.search)
        val searchView = searchMenuItem.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onRestart() {
        super.onRestart()

    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        val tempList = ArrayList<AppInfo>()
        for (user in mAppList!!) {
            if (user.packInfo.toLowerCase().contains(newText.toLowerCase())) {
                tempList.add(user)
            }
        }
        if (mRecylerView != null) {
            customAdaper = CustomAdaper(tempList, this@MainActivity,this)
            mRecylerView!!.adapter = customAdaper
        }
        return true
    }

    override fun onClick(v: View) {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.share -> try {
                sharePlayStoreLink(BuildConfig.APPLICATION_ID)
            }catch (e:Exception){

            }

            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun sharePlayStoreLink(pack:String)
    {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "ApkShare")
        var shareMessage = "\nLet me recommend you this application\n"
        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + pack + "\n\n"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        startActivity(Intent.createChooser(shareIntent, "choose one"))
    }
    override fun onItemClick(item: AppInfo) {
        var notificationManager:NoticeDialogFragments?=NoticeDialogFragments();
        notificationManager!!.show(supportFragmentManager,"NoticeDialogFragment")
        mApkClickedItem=item
    }
    fun shareapk(item: AppInfo)
    {
        val imageFile = File(item.apkPath)
        Log.d("VinayHere", "hereapk" + filesDir + item.apkPath)
        val intent = Intent(Intent.ACTION_SEND)
        var uri: Uri? = null
        intent.type = "*/*"
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            uri = Uri.fromFile(imageFile)
        } else {
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            uri = FileProvider.getUriForFile(applicationContext, ".provider",
                    imageFile)
        }
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(Intent.createChooser(intent, "Share app via"))
    }
    inner class loadApkList : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg voids: Void): Void? {
            val mList = packageManager.getInstalledPackages(0)
            Log.d("VinayAbhi","here"+mList.size);
           if(mList.size> mCount!!) {
               for (i in mList.indices) {
                   val appInfo = AppInfo()
                   val mPackageInfo = mList[i]
                   val mAppInfo = mPackageInfo.applicationInfo.loadLabel(packageManager).toString()
                   val mPackName = mPackageInfo.packageName
                   try {
                       val mApplicationInfo = packageManager.getApplicationInfo(mPackName, 0)
                       val mApk = mApplicationInfo.publicSourceDir
                       val mAppIcon = applicationContext.packageManager.getApplicationIcon(mPackName)
                       var bitmapdata: ByteArray? = null
                       if (mAppIcon is BitmapDrawable) {
                           val bitmap: Bitmap? = (mAppIcon as BitmapDrawable).bitmap
                           val stream: ByteArrayOutputStream? = ByteArrayOutputStream();
                           bitmap!!.compress(Bitmap.CompressFormat.PNG, 100, stream)
                           bitmapdata = stream!!.toByteArray()
                       }
                       val apkDetails: ApkDetails = ApkDetails();
                       if (!isSystemPackage(mPackageInfo)) {
                           var flag:Boolean?=false
                           if (!isSystemPackage(mPackageInfo)) {
                                if(apkMap!![mPackName]==1){
                                    flag = true
                                }
                               if(flag==false) {

                                   apkDetails.packageName = mPackName;
                                   apkDetails.appName = mAppInfo;
                                   apkDetails.appIconDrawable = bitmapdata
                                   listViewModel!!.insert(apkDetails)
                               }
                           }
                       }
                   } catch (e: PackageManager.NameNotFoundException) {
                       e.printStackTrace()
                   }
               }
           }
            else{
               return null
           }
            return null
        }
    }

}
