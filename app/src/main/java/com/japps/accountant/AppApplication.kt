package com.japps.accountant

import android.app.Application
import android.content.Context
import com.japps.accountant.room_db.AccountantRoomDb

class AppApplication : Application() {
    private var accountantRoomDb: AccountantRoomDb?=null

    init {
        instance = this
    }

    companion object{
        private var instance:AppApplication?=null

        fun getInstance():AppApplication{
            return instance!!
        }

    }
    fun getDb():AccountantRoomDb{
        if(accountantRoomDb==null){
            accountantRoomDb = AccountantRoomDb.getInstance(instance!!.applicationContext)
        }
        return  accountantRoomDb!!
    }

    override fun onCreate() {
        super.onCreate()
        val context:Context = AppApplication.getInstance()
    }
}