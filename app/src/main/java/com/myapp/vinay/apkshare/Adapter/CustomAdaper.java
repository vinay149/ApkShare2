package com.myapp.vinay.apkshare.Adapter;
import android.support.annotation.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.myapp.vinay.apkshare.*;
import com.myapp.vinay.apkshare.model.*;

import java.util.*;

public class CustomAdaper extends RecyclerView.Adapter<CustomAdaper.MyHolder> {
    private ArrayList<AppInfo> mArrayList;
    private OnItemClickListener listener=null;
    
    public CustomAdaper (ArrayList<AppInfo> mAppList,OnItemClickListener listener) {
       this.mArrayList=mAppList;
       this.listener=listener;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder (@NonNull ViewGroup viewGroup, int i) {
        
        View view=LayoutInflater.from (viewGroup.getContext ()).inflate (R.layout.apk_item,viewGroup,false);
        MyHolder myHolder=new MyHolder (view);
        return myHolder;
    }
    public interface OnItemClickListener {
        void onItemClick(AppInfo item);
    }
    @Override
    public void onBindViewHolder (@NonNull MyHolder myHolder, int i) {
       myHolder.mAppName.setText (mArrayList.get (i).getAppInfo ());
       myHolder.mPackageName.setText (mArrayList.get (i).getPackInfo ());
       myHolder.mAppIcon.setImageDrawable (mArrayList.get (i).getApkIcon ());
       myHolder.bind(mArrayList.get (i),listener);
    }
    @Override
    public int getItemCount () {
        return mArrayList.size ();
    }
    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView mAppName=null;
        private ImageView mAppIcon=null;
        public  TextView mPackageName=null;
        public MyHolder (@NonNull View itemView) {
            super (itemView);
           mAppName=itemView.findViewById (R.id.appName);
           mPackageName=itemView.findViewById (R.id.appPackage);
           mAppIcon=itemView.findViewById (R.id.appImage);
        }
        public void bind(final AppInfo appInfo,final OnItemClickListener listener)
        {
           itemView.setOnClickListener (new View.OnClickListener () {
               @Override
               public void onClick (View v) {
                    listener.onItemClick (appInfo);
               }
           });
        }
    }
}
