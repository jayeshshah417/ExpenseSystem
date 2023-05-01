package com.japps.accountant.room_db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.japps.accountant.models.AccountAndTag
import com.japps.accountant.models.AccountModel
import com.japps.accountant.models.AccountTagModel

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(account : AccountModel):Long

    @Update
    fun update(account : AccountModel) : Int

    @Query("select *, (`limit` - (select sum(debit-credit) from account_entry_table where acc_id = id) ) as balance from accountmodel")
    fun selectAccount():LiveData<List<AccountModel>>

}