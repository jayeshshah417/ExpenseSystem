package com.japps.accountant.ui.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.japps.accountant.R
import com.japps.accountant.adapters.SpinnerAdapter
import com.japps.accountant.databinding.DialogFormAccountBinding
import com.japps.accountant.databinding.DialogFormAccountEntryBinding
import com.japps.accountant.models.AccountEntryModel
import com.japps.accountant.models.AccountModel
import com.japps.accountant.models.AccountTagModel
import com.japps.accountant.models.BaseModels
import com.japps.accountant.room_db.AccountantViewModel
import com.japps.accountant.ui.home.HomeFragment
import com.japps.accountant.utils.DateUtils
import com.japps.accountant.utils.Utility
import com.japps.accountant.utils.Utility.Companion.capitalizeWords
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.logging.SimpleFormatter
import kotlin.collections.ArrayList

class NewAccountEntryDialog(context: Context, val accountantViewModel: AccountantViewModel,val supportFragmentManager: FragmentManager,val listAccount:List<AccountModel>) : Dialog(context) {

    private var viewBinding:DialogFormAccountEntryBinding
    private lateinit var adapter: SpinnerAdapter
    private lateinit var selectedTag:AccountModel
    private var accountEntryModel:AccountEntryModel? = null
    init {
        viewBinding = DialogFormAccountEntryBinding.inflate(layoutInflater)
    }
    public fun initData(accountModel: AccountEntryModel){
        this.accountEntryModel = accountModel
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        viewBinding.btSubmit.setOnClickListener( View.OnClickListener {
            if(!Utility.checkNotBlankField(viewBinding.etCredit.text.toString())){
                viewBinding.etCredit.setText("0")
            }
            if(!Utility.checkNotBlankField(viewBinding.etDebit.text.toString())){
                viewBinding.etDebit.setText("0")
            }
            if(Utility.checkNotBlankField(viewBinding.etName.text.toString()) && Utility.checkNotBlankField(viewBinding.etCredit.text.toString()) && Utility.checkNotBlankField(viewBinding.etDebit.text.toString())) {
                Toast.makeText(context, "On Submit ", Toast.LENGTH_SHORT).show()
                if(accountEntryModel==null) {
                    accountantViewModel.insertAccountEntry(
                        AccountEntryModel(
                            viewBinding.etName.text.toString().capitalizeWords(),
                            DateUtils.getStringToDate(viewBinding.textviewDate.text.toString()),
                            viewBinding.etDebit.text.toString().toDouble(),
                            viewBinding.etCredit.text.toString().toDouble(),
                            viewBinding.spAccount.selectedItem as AccountModel,
                        )
                    )
                }else{
                    accountEntryModel?.date = DateUtils.getStringToDate(viewBinding.textviewDate.text.toString())
                    accountEntryModel?.name = viewBinding.etName.text.toString()
                    accountEntryModel?.debit = viewBinding.etDebit.text.toString().toDouble()
                    accountEntryModel?.credit = viewBinding.etCredit.text.toString().toDouble()
                    accountEntryModel?.account_id = viewBinding.spAccount.selectedItem as AccountModel
                    accountantViewModel.updateAccountEntry(accountEntryModel!!)
                }
                this.dismiss()
            }

        })

        var listAccTag:ArrayList<BaseModels> = ArrayList<BaseModels>()
        listAccTag = listAccount as ArrayList<BaseModels> /* = java.util.ArrayList<com.japps.accountant.models.BaseModels> */
        adapter = SpinnerAdapter(this.context,HomeFragment.TYPE.ACCOUNT,R.layout.spinner_item_layout,listAccTag)
        viewBinding.spAccount.adapter = adapter

        adapter.notifyDataSetChanged()
        viewBinding.textviewDate.text = DateUtils.getCurrentDateToString(DateUtils.DATEFORMAT)
        viewBinding.textviewDate.setOnClickListener(View.OnClickListener {

            // on below line we are getting
            // the instance of our calendar.
            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)
            val second = c.get(Calendar.SECOND)
            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                context,
                { view, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our text view.
                    viewBinding.textviewDate.text = String.format("%4d-%2d-%2d",year,monthOfYear+1,dayOfMonth)
                    openTimePicker()
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            // at last we are calling show
            // to display our date picker dialog.
            datePickerDialog.show()
        })

        if(accountEntryModel!=null){
            checkinitData(accountEntryModel!!)
        }
    }

    private fun openTimePicker(){
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)
        val second = mcurrentTime.get(Calendar.SECOND)
        val timePickerDialog = TimePickerDialog(context, object : TimePickerDialog.OnTimeSetListener{
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                viewBinding.textviewDate.setText(String.format("%s %2d:%2d:%2d", viewBinding.textviewDate.text,hourOfDay, minute,second))
            }
        },hour,minute,true)
        timePickerDialog.show()
    }

    private fun checkinitData(accountModel: AccountEntryModel) {
        if(accountModel!=null){
            viewBinding.etName.setText(accountModel.name)
            viewBinding.textviewDate.setText( DateFormat.format(DateUtils.DATEFORMAT,accountModel.date ).toString())
            viewBinding.etDebit.setText(accountModel.debit.toString())
            viewBinding.etCredit.setText(accountModel.credit.toString())
            var i = 0
            for(e in listAccount){
                if(e.id == accountModel.account_id.id){
                    viewBinding.spAccount.setSelection(i)
                    break
                }
                i++
            }

        }
    }
}