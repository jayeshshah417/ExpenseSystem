package com.japps.accountant.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.japps.accountant.R
import com.japps.accountant.adapters.RecyclerViewAdapter
import com.japps.accountant.adapters.RecyclerViewAdapterAccount
import com.japps.accountant.adapters.RecyclerViewAdapterAccountEntry
import com.japps.accountant.databinding.ActivityAccountBinding
import com.japps.accountant.databinding.ActivityAccountEntryBinding
import com.japps.accountant.databinding.FragmentHomeBinding
import com.japps.accountant.models.AccountEntryModel
import com.japps.accountant.models.AccountModel
import com.japps.accountant.models.BaseModels
import com.japps.accountant.room_db.AccountantViewModel
import com.japps.accountant.ui.dialog.NewAccountEntryDialog
import com.japps.accountant.ui.home.HomeFragment
import com.japps.accountant.utils.DateUtils
import com.japps.accountant.utils.Utility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class AccountEntryActivity : AppCompatActivity(), RecyclerViewAdapterAccountEntry.RowOnClickListener {
    private lateinit var binding: ActivityAccountEntryBinding
    private lateinit var recyclerViewAdapter:RecyclerViewAdapterAccountEntry
    private var fromDate:Date?= null
    private var toDate:Date?= null
    private lateinit var viewModelProvider:AccountantViewModel
    private var listAccount:List<AccountModel> = ArrayList()
    init {

    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitle("Account Entry")


        recyclerViewAdapter = RecyclerViewAdapterAccountEntry(ArrayList<BaseModels>(),HomeFragment.TYPE.ACCOUNT,this)
        binding.rvData.layoutManager = LinearLayoutManager(this)
        binding.rvData.adapter = recyclerViewAdapter
        recyclerViewAdapter.notifyDataSetChanged()

       viewModelProvider = ViewModelProvider(this).get(AccountantViewModel::class.java)
        viewModelProvider.getAllAccount().observe(this,{
            listAccount = it
        })
        //val viewModel = viewModelProvider.getAllAccountEntry().observe(this,
       /* val viewModel = viewModelProvider.getAllAccountEntryDate(fromDate!!,toDate!!).observe(this,
            Observer {
                if(it!=null) {
                    recyclerViewAdapter.setData(it)
                    recyclerViewAdapter.notifyDataSetChanged()

                }})
*/
        val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker().setSelection(Pair.create(MaterialDatePicker.todayInUtcMilliseconds(),MaterialDatePicker.todayInUtcMilliseconds())).build()

        dateRangePicker.addOnPositiveButtonClickListener {
            fromDate = Date(it.first)
            toDate = Date(it.second)
            val from_date =  DateFormat.format(DateUtils.DATEFORMAT_from,fromDate ).toString()
            val to_date =  DateFormat.format(DateUtils.DATEFORMAT_to, toDate).toString()
            binding.tvDateFrom.text =from_date
            binding.tvDateTo.text = to_date
            fromDate = DateUtils.getStringToDate(from_date)
            toDate = DateUtils.getStringToDate(to_date)
            loadData(viewModelProvider, fromDate!!, toDate!!)


        }
        binding.tvDateFrom.setOnClickListener(View.OnClickListener { dateRangePicker.show(this.supportFragmentManager,"Tag") })
        binding.tvDateTo.setOnClickListener(View.OnClickListener { dateRangePicker.show(this.supportFragmentManager,"Tag") })
        binding.tvDateFrom.text = DateUtils.getCurrentDateToString(DateUtils.DATEFORMAT_from)
        binding.tvDateTo.text = DateUtils.getCurrentDateToString(DateUtils.DATEFORMAT_to)
        fromDate = DateUtils.getStringToDate(binding.tvDateFrom.text.toString())
        toDate = DateUtils.getStringToDate(binding.tvDateTo.text.toString())
        loadData(viewModelProvider, fromDate!!, toDate!!)

    }

    override fun onRowClick(position: Int) {
        val accountDialog = NewAccountEntryDialog(this, accountantViewModel = viewModelProvider,this.supportFragmentManager, listAccount = listAccount)
        accountDialog.initData(recyclerViewAdapter.itemList.get(position) as AccountEntryModel)
        accountDialog.show()
        val accWindow = accountDialog.window
        accWindow!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }
    private fun loadData(viewModelProvider:AccountantViewModel,fromDate: Date,toDate: Date){
        viewModelProvider.getAllAccountEntryDate(fromDate,toDate).observe(this,
            Observer {
                if(it!=null) {
                    recyclerViewAdapter.setData(it)
                    recyclerViewAdapter.notifyDataSetChanged()

                }})
    }
}