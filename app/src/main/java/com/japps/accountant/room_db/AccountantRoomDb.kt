package com.japps.accountant.room_db

import android.content.Context
import androidx.room.*
import com.japps.accountant.models.AccountEntryModel
import com.japps.accountant.models.AccountModel
import com.japps.accountant.models.AccountTagModel
import com.japps.accountant.models.DateConverter
import com.japps.accountant.room_db.dao.AccountDao
import com.japps.accountant.room_db.dao.AccountEntryDao
import com.japps.accountant.room_db.dao.AccountTagDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = arrayOf(AccountModel::class,AccountEntryModel::class,AccountTagModel::class), version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AccountantRoomDb : RoomDatabase() {
    abstract fun accountDao():AccountDao
    abstract fun accountEntryDao():AccountEntryDao
    abstract fun accountTagDao(): AccountTagDao


    companion object{
        private var instance : AccountantRoomDb?=null
        private val NUMBER_OF_THREADS = 4
        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        fun getInstance(ctx: Context): AccountantRoomDb {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, AccountantRoomDb::class.java,
                    "accountant_database"
                ).build()

            return instance!!
        }
    }

}