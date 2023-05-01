package com.japps.accountant.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.japps.accountant.R
import com.japps.accountant.models.AccountModel
import com.japps.accountant.models.AccountTagModel
import com.japps.accountant.models.BaseModels
import com.japps.accountant.ui.home.HomeFragment

class RecyclerViewAdapterAccountTag(var itemList:List<BaseModels>, val type:HomeFragment.TYPE, val rowOnClickListener: RowOnClickListener) : RecyclerView.Adapter<RecyclerViewAdapterAccountTag.MyViewHolder>() {
    public interface RowOnClickListener{
        public fun onRowClick(position:Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_account_tag_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val item =  itemList.get (position) as AccountTagModel
            holder.tv_name.text =item.name
            holder.cb_add_to_dashboard.isChecked = item.addToDashboard

        holder.itemView.setOnClickListener(View.OnClickListener {
            rowOnClickListener.onRowClick(holder.adapterPosition)
        })
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_name :TextView = itemView.findViewById(R.id.tv_name)
        val cb_add_to_dashboard :CheckBox = itemView.findViewById(R.id.cb_add_to_dashboard)

    }

    fun setData( data:List<BaseModels>){
        this.itemList = data
    }


}

