package com.japps.accountant.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.japps.accountant.R
import com.japps.accountant.databinding.DialogFormAccountTagBinding
import com.japps.accountant.models.AccountTagModel
import com.japps.accountant.room_db.AccountantViewModel
import com.japps.accountant.utils.Utility

class NewAccountTagDialog(context: Context,val accountantViewModel: AccountantViewModel) : Dialog(context) {
    private var accountTagModel: AccountTagModel? = null
    private var viewBinding:DialogFormAccountTagBinding
    init {
        viewBinding = DialogFormAccountTagBinding.inflate(layoutInflater)
    }
    public fun initData(accountTagModel: AccountTagModel){
        this.accountTagModel = accountTagModel
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        viewBinding.btSubmit.setOnClickListener( View.OnClickListener {
            if(Utility.checkNotBlankField(viewBinding.etName.text.toString())) {
                Toast.makeText(context, "On Submit ", Toast.LENGTH_SHORT).show()
                if(accountantViewModel!=null){
                    if(accountTagModel==null) {
                        accountantViewModel.insertTag(
                            AccountTagModel(
                                viewBinding.etName.text.toString().uppercase(),
                                viewBinding.cbAddToDashboard.isChecked
                            )
                        )
                    }else{
                        accountTagModel!!.name = viewBinding.etName.text.toString()
                        accountTagModel!!.addToDashboard = viewBinding.cbAddToDashboard.isChecked
                        accountantViewModel.updateTag(
                            accountTagModel!!
                        )
                    }
                    this.dismiss()
                }

            }

        })
if(accountTagModel!=null) {
    checkinitData(accountTagModel!!)
}
    }

    private fun checkinitData(accountTagModel: AccountTagModel) {
        if(accountTagModel!=null){
            viewBinding.etName.setText(accountTagModel.name)
            viewBinding.cbAddToDashboard.isChecked = accountTagModel.addToDashboard
        }
    }
}