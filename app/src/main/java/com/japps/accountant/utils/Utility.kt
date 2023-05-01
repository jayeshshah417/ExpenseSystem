package com.japps.accountant.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.japps.accountant.activity.AccountActivity
import com.japps.accountant.activity.AccountEntryActivity
import com.japps.accountant.activity.AccountTagActivity
import com.japps.accountant.activity.MainActivity
import java.util.*

class Utility {
    companion object{
        public fun getScreen(context: Context, position:Int){
            lateinit var intent:Intent
            intent = when(position){
                0-> Intent(context, AccountActivity::class.java)
                1 -> Intent(context, AccountEntryActivity::class.java)
                2 -> Intent(context, AccountTagActivity::class.java)
                else -> {Intent(context, MainActivity::class.java)}
            }
            context.startActivity(intent)
        }

        public fun checkNotBlankField(value:String):Boolean{
            if(value!=null && !value.equals("")){
                return true
            }
            return false
        }

        public fun String.capitalizeWords(): String = split(" ").map { it.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        } }.joinToString(" ")

        public fun showToast( context: Context, msg: String){
            Toast.makeText(context!!,msg,Toast.LENGTH_SHORT).show()
        }
    }
}