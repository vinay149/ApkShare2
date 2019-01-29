package com.myapp.vinay.apkshare

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast

class NoticeDialogFragments : DialogFragment() {

    // Use this instance of the interface to deliver action events
    internal lateinit var mListener: NoticeDialogListener
    var view1:View?=null
    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */

    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: Dialog,int: Int)
        fun onDialogNegativeClick(dialog: Dialog)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        view1=inflater.inflate(R.layout.share_dailog, container, false)
        var tvCancel:TextView = view1!!.findViewById(R.id.textView4)
        var rbApkShare:RadioButton= view1!!.findViewById(R.id.radioButton3)
        var rbLinkShare:RadioButton= view1!!.findViewById(R.id.radioButton4)
        var rgShare:RadioGroup = view1!!.findViewById(R.id.radioGroup)
        var flag:Int?=null
        rgShare.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    val radio: RadioButton = view1!!.findViewById(checkedId)
                    Toast.makeText(context," On checked change : ${radio.text}",
                            Toast.LENGTH_SHORT).show()
                })
        tvCancel.setOnClickListener {
            mListener.onDialogNegativeClick(dialog)
        }
        var tvShare:TextView = view1!!.findViewById(R.id.textView3)
        tvShare.setOnClickListener {
            if(rbApkShare.isChecked){
                flag=0
            }else if(rbLinkShare.isChecked){
                flag=1
            }
            Log.d("VinayChecked","here"+flag);
            if(flag!=null) {
                mListener.onDialogPositiveClick(dialog, flag!!)
            }
        }
        return view1 as View
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog=super.onCreateDialog(savedInstanceState)
        return dialog
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = context as NoticeDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }

}