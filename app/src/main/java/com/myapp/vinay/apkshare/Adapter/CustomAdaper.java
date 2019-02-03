package com.myapp.vinay.apkshare.Adapter;
import android.content.Context;
import android.support.annotation.*;
import android.support.v7.widget.*;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.myapp.vinay.apkshare.*;
import com.myapp.vinay.apkshare.glide.GlideApp;
import com.myapp.vinay.apkshare.model.*;

import java.util.*;

public class CustomAdaper extends RecyclerView.Adapter<CustomAdaper.MyHolder>  implements  Filterable{
    private ArrayList<AppInfo> mArrayList;
    private OnItemClickListener listener=null;
    public ArrayList<AppInfo> filteredList=null;
    private CustomFilter customFilter=null;
    private Context context=null;
    public CustomAdaper (ArrayList<AppInfo> mAppList, OnItemClickListener listener, Context context) {
       this.mArrayList=mAppList;
       this.listener=listener;
       this.context=context;
       this.filteredList=mAppList;
       getFilter ();
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder (@NonNull ViewGroup viewGroup, int i) {
        
        View view=LayoutInflater.from (viewGroup.getContext ()).inflate (R.layout.apk_item,viewGroup,false);
        MyHolder myHolder=new MyHolder (view);
        return myHolder;
    }
    
    @Override
    public Filter getFilter () {
        Log.d("VinayKumar","here");
        if(customFilter==null)
        {
            customFilter=new CustomFilter();
        }
        return customFilter;
    }
    private class CustomFilter extends Filter {
        
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<AppInfo> tempList = new ArrayList<AppInfo>();
                Log.d("VinayKumar","Vinay");
                // search content in friend list
                for (AppInfo user : mArrayList) {
                    if (user.getPackInfo ().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(user);
                    }
                }
                
                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = mArrayList.size();
                filterResults.values = mArrayList;
            }
            
            return filterResults;
        }
        
        /**
         * Notify about filtered list to ui
         * @param constraint text
         * @param results filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<AppInfo>) results.values;
            Log.d("VinayKumar","Vinay"+filteredList.get (0).getAppInfo ());
            CustomAdaper customAdaper=new CustomAdaper (filteredList,listener,context);
        }
    }
    
    
    public interface OnItemClickListener {
        void onItemClick(AppInfo item);
    }
    @Override
    public void onBindViewHolder (@NonNull MyHolder myHolder, int i) {
       myHolder.mAppName.setText (mArrayList.get (i).getAppInfo ());
       myHolder.mPackageName.setText (mArrayList.get (i).getPackInfo ());
      // myHolder.mAppIcon.setImageDrawable (mArrayList.get (i).getApkIcon ());
        GlideApp.with(context).load (mArrayList.get (i).getApkIcon ()).circleCrop ().into (myHolder.mAppIcon);
    
        Log.d("Comehereapk","here13234"+mArrayList.get (i).getApkPath ());
       myHolder.bind(mArrayList.get (i),listener);
    }
    @Override
    public int getItemCount () {
        return mArrayList.size ();
    }
    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView mAppName = null;
        private ImageView mAppIcon = null;
        public  TextView mPackageName = null;
        private ImageView mImageView = null;
        public MyHolder (@NonNull View itemView) {
            super (itemView);
           mAppName=itemView.findViewById (R.id.appName);
           mPackageName=itemView.findViewById (R.id.appPackage);
           mAppIcon=itemView.findViewById (R.id.appImage);
           mImageView=itemView.findViewById (R.id.shareImg);
        }
        public void bind(final AppInfo appInfo,final OnItemClickListener listener)
        {
            mImageView.setOnClickListener (new View.OnClickListener () {
               @Override
               public void onClick (View v) {
                   
                   Log.d("Comehereapk","here132"+appInfo.getApkPath ());
                   listener.onItemClick (appInfo);
               }
           });
        }
    }
}
