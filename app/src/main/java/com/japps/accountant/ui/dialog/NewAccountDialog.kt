package com.japps.accountant.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.japps.accountant.R
import com.japps.accountant.adapters.SpinnerAdapter
import com.japps.accountant.databinding.DialogFormAccountBinding
import com.japps.accountant.models.AccountModel
import com.japps.accountant.models.AccountTagModel
import com.japps.accountant.models.BaseModels
import com.japps.accountant.room_db.AccountantViewModel
import com.japps.accountant.ui.home.HomeFragment
import com.japps.accountant.utils.Utility

class NewAccountDialog(context: Context,val accountantViewModel: AccountantViewModel,val listTag:List<AccountTagModel>) : Dialog(context) {

    private var viewBinding:DialogFormAccountBinding
    private lateinit var adapter: SpinnerAdapter
    private lateinit var selectedTag:AccountTagModel
    private var accountModel: AccountModel? = null
    init {
        viewBinding = DialogFormAccountBinding.inflate(layoutInflater)
    }
    public fun initData(accountModel: AccountModel){
        this.accountModel = accountModel
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        viewBinding.btSubmit.setOnClickListener( View.OnClickListener {
            if(Utility.checkNotBlankField(viewBinding.etName.text.toString()) && Utility.checkNotBlankField(viewBinding.etLimit.text.toString())) {
                Toast.makeText(context, "On Submit ", Toast.LENGTH_SHORT).show()
                if(accountModel==null) {
                    accountantViewModel.insertAccount(
                        AccountModel(
                            viewBinding.etName.text.toString().uppercase(),
                            viewBinding.etLimit.text.toString().toDouble(),
                            0.0,
                            viewBinding.spTags.selectedItem as AccountTagModel
                        )
                    )
                }else{
                    accountModel!!.name = viewBinding.etName.text.toString()
                    accountModel!!.limit = viewBinding.etLimit.text.toString().toDouble()
                    accountModel!!.tag_id = viewBinding.spTags.selectedItem as AccountTagModel
                    accountantViewModel.updateAccount(accountModel!!)
                }
                this.dismiss()
            }
        })

        var listAccTag:ArrayList<BaseModels> = ArrayList<BaseModels>()
        listAccTag = listTag as ArrayList<BaseModels> /* = java.util.ArrayList<com.japps.accountant.models.BaseModels> */
        adapter = SpinnerAdapter(this.context,HomeFragment.TYPE.ACCOUNT_TAG,R.layout.spinner_item_layout,listAccTag)
        viewBinding.spTags.adapter = adapter

        adapter.notifyDataSetChanged()

        if(accountModel!=null){
            checkinitData(accountModel!!)
        }
    }

    private fun checkinitData(accountModel: AccountModel) {
        if(accountModel!=null){
            viewBinding.etName.setText(accountModel.name)
            viewBinding.etLimit.setText(accountModel.limit.toString())
            var i = 0
            for(e in listTag){
                if(e.id == accountModel.tag_id.id){
                    viewBinding.spTags.setSelection(i)
                    break
                }
                i++
            }

        }
    }
}