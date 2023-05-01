package com.japps.accountant.room_db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.japps.accountant.models.AccountAndTag
import com.japps.accountant.models.AccountModel
import com.japps.accountant.models.AccountTagModel

@Dao
interface AccountTagDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(account : AccountTagModel) : Long

    @Update
    fun update(account : AccountTagModel) : Int

    @Query("select * from account_tag_table")
    fun selectAccountTag():LiveData<List<AccountTagModel>>

}