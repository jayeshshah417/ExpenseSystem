package com.japps.accountant.room_db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.japps.accountant.models.AccountAndTag
import com.japps.accountant.models.AccountEntryModel
import com.japps.accountant.models.AccountModel
import java.util.Date

@Dao
interface AccountEntryDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(account : AccountEntryModel):Long

    @Update
    fun update(account : AccountEntryModel):Int

    @Query("select * from account_entry_table order by date")
    fun selectAccountEntry():LiveData<List<AccountEntryModel>>

    @Query("select * from account_entry_table where date>= :fromDate and date<= :toDate order by date")
    fun selectAccountEntryDate(fromDate:Date,toDate:Date):LiveData<List<AccountEntryModel>>

}