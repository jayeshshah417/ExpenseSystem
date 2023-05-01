package com.japps.accountant.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.japps.accountant.models.AccountModel
import com.japps.accountant.models.AccountTagModel
import com.japps.accountant.models.BaseModels
import com.japps.accountant.ui.home.HomeFragment

class SpinnerAdapter(context: Context,val type:HomeFragment.TYPE, resource: Int,var listModel: ArrayList<BaseModels>) :
    ArrayAdapter<BaseModels>(context, resource,listModel) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val textview:TextView =  super.getView(position, convertView, parent) as TextView
        if(type==HomeFragment.TYPE.ACCOUNT_TAG){
            textview.text = (this.listModel.get(position) as AccountTagModel).name
        }else if (type==HomeFragment.TYPE.ACCOUNT){
            textview.text = (this.listModel.get(position) as AccountModel).name
        }
        return textview
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val textview:TextView = super.getDropDownView(position, convertView, parent) as TextView
        if(type==HomeFragment.TYPE.ACCOUNT_TAG){
            textview.text = (this.listModel.get(position) as AccountTagModel).name
        }else if (type==HomeFragment.TYPE.ACCOUNT){
            textview.text = (this.listModel.get(position) as AccountModel).name
        }

        return textview
    }

}