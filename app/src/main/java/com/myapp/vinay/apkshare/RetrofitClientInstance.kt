package com.myapp.vinay.apkshare

import com.myapp.vinay.apkshare.Utils.Utils
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClientInstance {
    companion object {
        private var retrofit:Retrofit?=null
        private var retrofit1:Retrofit?=null
        private  var base_url:String?="https://my.api.mockaroo.com"
        private  var base_url_post:String?="http://192.168.0.104:9000"
        //val cacheSize = (5 * 1024 * 1024).toLong()
      //  val myCache = Cache(.cacheDir, cacheSize)
//        val okHttpClient = OkHttpClient.Builder()
//                .cache(myCache)
//                .addInterceptor { chain ->
//                    var request = chain.request()
//                    request = if (Utils.hasNetwork()!!)
//                        request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
//                    else
//                        request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
//                    chain.proceed(request)
//                }
//                .build()
        fun getRetrofitInstance():Retrofit{
            if(retrofit == null)
            {
                retrofit=retrofit2.Retrofit.Builder().baseUrl(base_url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit!!
        }
        fun getRetrofitInstancePost():Retrofit{
            if(retrofit1 == null)
            {
                retrofit1=retrofit2.Retrofit.Builder().baseUrl(base_url_post)
                        .addConverterFactory(GsonConverterFactory.create()).build()
            }
            return retrofit1!!
        }
    }
}