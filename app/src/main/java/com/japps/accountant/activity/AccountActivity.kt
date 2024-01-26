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
import com.japps.accountant.databinding.ActivityAccountBinding
import com.japps.accountant.databinding.FragmentHomeBinding
import com.japps.accountant.models.AccountModel
import com.japps.accountant.models.AccountTagModel
import com.japps.accountant.models.BaseModels
import com.japps.accountant.room_db.AccountantViewModel
import com.japps.accountant.ui.dialog.NewAccountDialog
import com.japps.accountant.ui.dialog.NewAccountTagDialog
import com.japps.accountant.ui.home.HomeFragment
import com.japps.accountant.utils.Utility

class AccountActivity : AppCompatActivity(), RecyclerViewAdapterAccount.RowOnClickListener {
    private lateinit var binding: ActivityAccountBinding
    private lateinit var recyclerViewAdapter:RecyclerViewAdapterAccount
    private lateinit var viewModel: AccountantViewModel
    private var listTags: List<AccountTagModel> = ArrayList()
    init {

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("Accounts")

        viewModel = ViewModelProvider(this).get(AccountantViewModel::class.java)
        viewModel.getAllAccount().observe(this,
            Observer {
                recyclerViewAdapter.setData(it)
                recyclerViewAdapter.notifyDataSetChanged()
            })
        viewModel.getAllTags().observe(this,
            Observer {
                listTags = it
            })
        recyclerViewAdapter = RecyclerViewAdapterAccount(ArrayList<BaseModels>(),HomeFragment.TYPE.ACCOUNT,this)
        binding.rvData.layoutManager = LinearLayoutManager(this)
        binding.rvData.adapter = recyclerViewAdapter
        recyclerViewAdapter.notifyDataSetChanged()

    }

    override fun onRowClick(position: Int) {
        val tagDialog = NewAccountDialog(this@AccountActivity,viewModel,listTags)
        tagDialog.initData(recyclerViewAdapter.itemList.get(position) as AccountModel)
        tagDialog.show()
        val accWindow = tagDialog.window
        accWindow!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }
}