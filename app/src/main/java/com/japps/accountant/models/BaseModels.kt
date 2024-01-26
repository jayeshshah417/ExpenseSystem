package com.japps.accountant.models

import android.content.ContentValues.TAG
import android.nfc.Tag
import android.util.Log
import androidx.room.*
import java.util.*

public class DateConverter {
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        Log.d(TAG,"To Date")
        return if (dateLong == null) null else Date(dateLong)
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        Log.d(TAG,"From Date")
        return date?.time
    }
}

open class BaseModels {
}
/*@Entity(tableName = "account_table",
    foreignKeys = arrayOf(ForeignKey(entity = AccountTagModel::class, parentColumns = arrayOf("id"), childColumns = arrayOf("tag_id"))),
    indices = arrayOf(Index("tag_id"))
)*/
@Entity
public data class AccountModel(
    @ColumnInfo(name="name")
    var name:String,
    @ColumnInfo(name="limit")
    var limit:Double,
    @ColumnInfo(name="balance")
    var balance:Double,
    @Embedded(prefix = "acc_tag_")
    var tag_id:AccountTagModel
): BaseModels() {
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0

}
@Entity(tableName = "account_tag_table")
data class AccountTagModel(var name:String,var addToDashboard:Boolean): BaseModels() {
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}

@Entity(tableName = "account_entry_table")
//@TypeConverters(DateConverter::class)
class AccountEntryModel(
    @ColumnInfo(name="name")
    var name:String,
    @ColumnInfo(name="date")
    //@TypeConverters(DateConverter::class)
    var date:Date,
    @ColumnInfo(name="debit")
    var debit:Double,
    @ColumnInfo(name="credit")
    var credit:Double,
    @Embedded(prefix = "acc_")
    var account_id:AccountModel): BaseModels() {
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0


}

data class EntryAndAccountAndTag(
    @Embedded
    val accountTagModel: AccountTagModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "tag_id"
    )
    @Embedded
    val accountModel: AccountModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "account_id"
    )
    val entryModel: AccountEntryModel
)

data class EntryAndAccount(
    @Embedded
    val accountModel: AccountModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "account_id"
    )
    val entryModel: AccountEntryModel
)

data class AccountAndTag(
    @Embedded
    val accountTagModel: AccountTagModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "tag_id"
    )
    val accountModel: AccountModel,

)