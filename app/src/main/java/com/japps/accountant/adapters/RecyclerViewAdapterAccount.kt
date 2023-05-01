package com.japps.accountant.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.japps.accountant.R
import com.japps.accountant.models.AccountModel
import com.japps.accountant.models.BaseModels
import com.japps.accountant.ui.home.HomeFragment

class RecyclerViewAdapterAccount(var itemList:List<BaseModels>, val type:HomeFragment.TYPE,val rowOnClickListener: RowOnClickListener) : RecyclerView.Adapter<RecyclerViewAdapterAccount.MyViewHolder>() {
    public interface RowOnClickListener{
        public fun onRowClick(position:Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_account_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val item =  itemList.get (position) as AccountModel
            holder.tv_name.text =item.name
            holder.tv_limit.text = item.limit.toString()
            holder.tv_balance.text = item.balance.toString()
            holder.tv_tag.text = item.tag_id.name

        holder.itemView.setOnClickListener(View.OnClickListener {
            rowOnClickListener.onRowClick(holder.adapterPosition)
        })
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_name :TextView = itemView.findViewById(R.id.tv_name)
        val tv_limit :TextView = itemView.findViewById(R.id.tv_limit)
        val tv_balance :TextView = itemView.findViewById(R.id.tv_balance)
        val tv_tag:TextView = itemView.findViewById(R.id.tv_tag)
    }

    fun setData( data:List<BaseModels>){
        this.itemList = data
    }


}

