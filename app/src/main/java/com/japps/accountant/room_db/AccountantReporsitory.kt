package com.japps.accountant.room_db

import android.util.Log
import androidx.lifecycle.LiveData
import com.japps.accountant.AppApplication
import com.japps.accountant.models.AccountEntryModel
import com.japps.accountant.models.AccountModel
import com.japps.accountant.models.AccountTagModel
import com.japps.accountant.room_db.dao.AccountDao
import com.japps.accountant.room_db.dao.AccountEntryDao
import com.japps.accountant.room_db.dao.AccountTagDao
import java.util.Date


class AccountantReporsitory(application: AppApplication) {
    private lateinit var accountTagDao:AccountTagDao
    private lateinit var accountDao: AccountDao
    private lateinit var accountEntryDao: AccountEntryDao
    private var accTagList: LiveData<List<AccountTagModel>>? = null
    private var accEntryList: LiveData<List<AccountEntryModel>>? = null
    private var accEntryDateList: LiveData<List<AccountEntryModel>>? = null
    private var accList: LiveData<List<AccountModel>>? = null
    init {
        val db = application.getDb()
        accountTagDao = db.accountTagDao()
        accountDao = db.accountDao()
        accountEntryDao = db.accountEntryDao()
        accTagList = accountTagDao.selectAccountTag()
        accList = accountDao.selectAccount()
        accEntryList = accountEntryDao.selectAccountEntry()
    }

    fun getTagList():LiveData<List<AccountTagModel>>{
        return accTagList!!
    }

    fun insertTag(accountTagModel: AccountTagModel){
        AccountantRoomDb.databaseWriteExecutor.execute(Runnable {
            accountTagDao.insert(accountTagModel)
        })
    }

    fun updateTag(accountTagModel: AccountTagModel){
        AccountantRoomDb.databaseWriteExecutor.execute(Runnable {
            accountTagDao.update(accountTagModel)
        })
    }

    fun insertAccount(accountModel: AccountModel){
        AccountantRoomDb.databaseWriteExecutor.execute(Runnable {
            accountDao.insert(accountModel)
        })
    }

    fun updateAccount(accountModel: AccountModel){
        AccountantRoomDb.databaseWriteExecutor.execute(Runnable {
            accountDao.update(accountModel)
        })
    }

    fun getAllAccount():LiveData<List<AccountModel>>{
        return accList!!
    }

    fun insertAccountEntry(accountEntryModel: AccountEntryModel) {
        AccountantRoomDb.databaseWriteExecutor.execute(Runnable {
            val long = accountEntryDao.insert(accountEntryModel)
            if(long>0){
                Log.d("Insert Entry ",long.toString())
            }
        })
    }
    fun updateAccountEntry(accountEntryModel: AccountEntryModel) {
        AccountantRoomDb.databaseWriteExecutor.execute(Runnable {
            val long = accountEntryDao.update(accountEntryModel)
            if(long>0){
                Log.d("Insert Entry ",long.toString())
            }
        })
    }

    fun getAllAccountEntry(): LiveData<List<AccountEntryModel>> {
        return accEntryList!!
    }

    fun getAllAccountEntryDate(fromDate:Date,toDate:Date): LiveData<List<AccountEntryModel>> {
        return accountEntryDao.selectAccountEntryDate(fromDate,toDate)!!
    }


}