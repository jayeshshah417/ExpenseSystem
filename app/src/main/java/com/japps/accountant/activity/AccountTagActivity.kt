package com.japps.accountant.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.japps.accountant.R
import com.japps.accountant.adapters.RecyclerViewAdapter
import com.japps.accountant.adapters.RecyclerViewAdapterAccount
import com.japps.accountant.adapters.RecyclerViewAdapterAccountTag
import com.japps.accountant.databinding.ActivityAccountBinding
import com.japps.accountant.databinding.FragmentHomeBinding
import com.japps.accountant.models.AccountTagModel
import com.japps.accountant.models.BaseModels
import com.japps.accountant.room_db.AccountantViewModel
import com.japps.accountant.ui.dialog.NewAccountTagDialog
import com.japps.accountant.ui.home.HomeFragment

class AccountTagActivity : AppCompatActivity(), RecyclerViewAdapterAccountTag.RowOnClickListener {
    private lateinit var binding: ActivityAccountBinding
    private lateinit var viewModel: AccountantViewModel
    private lateinit var recyclerViewAdapter:RecyclerViewAdapterAccountTag
    init {

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        setTitle("Account Tag")
        recyclerViewAdapter = RecyclerViewAdapterAccountTag(ArrayList<BaseModels>(),HomeFragment.TYPE.ACCOUNT,this)
        binding.rvData.layoutManager = LinearLayoutManager(this)
        binding.rvData.adapter = recyclerViewAdapter
        recyclerViewAdapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(AccountantViewModel::class.java)
        viewModel.getAllTags().observe(this,
            Observer {
                recyclerViewAdapter.setData(it)
                recyclerViewAdapter.notifyDataSetChanged()
            })
    }

    override fun onRowClick(position: Int) {
        val tagDialog = NewAccountTagDialog(this,viewModel)
        tagDialog.initData(recyclerViewAdapter.itemList.get(position) as AccountTagModel)
        tagDialog.show()
        val accWindow = tagDialog.window
        accWindow!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }
}