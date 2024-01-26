package com.japps.accountant.room_db

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.japps.accountant.AppApplication
import com.japps.accountant.models.AccountEntryModel
import com.japps.accountant.models.AccountModel
import com.japps.accountant.models.AccountTagModel
import java.util.Date

class AccountantViewModel: ViewModel() {
    private lateinit var accountantReporsitory: AccountantReporsitory
    private final lateinit var accountTagModelList :LiveData<List<AccountTagModel>>
    private final lateinit var accountEntryModelList: LiveData<List<AccountEntryModel>>
    private final lateinit var accountModelList :LiveData<List<AccountModel>>
    init {
        accountantReporsitory = AccountantReporsitory(AppApplication.getInstance())
        accountTagModelList = accountantReporsitory.getTagList()
        accountModelList = accountantReporsitory.getAllAccount()
        accountEntryModelList = accountantReporsitory.getAllAccountEntry()
    }

    fun getAllTags():LiveData<List<AccountTagModel>>{
        return accountTagModelList
    }

    fun insertTag(accountTagModel: AccountTagModel){
        accountantReporsitory.insertTag(accountTagModel)
    }

    fun updateTag(accountTagModel: AccountTagModel){
        accountantReporsitory.updateTag(accountTagModel)
    }

    fun insertAccount(accountModel: AccountModel){
        accountantReporsitory.insertAccount(accountModel)
    }

    fun updateAccount(accountModel: AccountModel){
        accountantReporsitory.updateAccount(accountModel)
    }

    fun getAllAccount():LiveData<List<AccountModel>>{
       return accountModelList
    }

    fun insertAccountEntry(accountEntryModel: AccountEntryModel){
        accountantReporsitory.insertAccountEntry(accountEntryModel)
    }
    fun updateAccountEntry(accountEntryModel: AccountEntryModel){
        accountantReporsitory.updateAccountEntry(accountEntryModel)
    }

    fun getAllAccountEntry():LiveData<List<AccountEntryModel>>{
        return accountEntryModelList
    }

    fun getAllAccountEntryDate(fromDate: Date,toDate:Date ):LiveData<List<AccountEntryModel>>{
        return accountantReporsitory.getAllAccountEntryDate(fromDate,toDate)
    }

}