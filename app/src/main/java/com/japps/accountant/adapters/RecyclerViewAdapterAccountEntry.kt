package com.japps.accountant.adapters

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.japps.accountant.R
import com.japps.accountant.models.AccountEntryModel
import com.japps.accountant.models.AccountModel
import com.japps.accountant.models.BaseModels
import com.japps.accountant.ui.home.HomeFragment
import com.japps.accountant.utils.DateUtils
import java.util.Calendar

class RecyclerViewAdapterAccountEntry(var itemList:List<BaseModels>, val type:HomeFragment.TYPE, val rowOnClickListener: RowOnClickListener) : RecyclerView.Adapter<RecyclerViewAdapterAccountEntry.MyViewHolder>() {
    public interface RowOnClickListener{
        public fun onRowClick(position:Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_account_entry_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val item =  itemList.get (position) as AccountEntryModel
            holder.tv_name.text =item.name
        val date =  DateFormat.format(DateUtils.DATEFORMAT,item.date ).toString()
            holder.tv_date.text = date
            holder.tv_debit.text = item.debit.toString()
            holder.tv_credit.text = item.credit.toString()
            holder.tv_account.text = item.account_id.name

        holder.itemView.setOnClickListener(View.OnClickListener {
            rowOnClickListener.onRowClick(holder.adapterPosition)
        })
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_name :TextView = itemView.findViewById(R.id.tv_name)
        val tv_date :TextView = itemView.findViewById(R.id.tv_date)
        val tv_debit :TextView = itemView.findViewById(R.id.tv_debit)
        val tv_credit :TextView = itemView.findViewById(R.id.tv_credit)
        val tv_account:TextView = itemView.findViewById(R.id.tv_account)
    }

    fun setData( data:List<BaseModels>){
        this.itemList = data
    }


}

