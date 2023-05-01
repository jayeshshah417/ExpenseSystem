package com.japps.accountant.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.japps.accountant.R

class RecyclerViewAdapter(val itemList:List<String>, val rowOnClickListener: RowOnClickListener) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    public interface RowOnClickListener{
        public fun onRowClick(position:Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tv_name.text = itemList.get(position)
        holder.itemView.setOnClickListener( View.OnClickListener {
            rowOnClickListener.onRowClick(holder.adapterPosition)
        })
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_name :TextView = itemView.findViewById(R.id.tv_name)
    }


}

