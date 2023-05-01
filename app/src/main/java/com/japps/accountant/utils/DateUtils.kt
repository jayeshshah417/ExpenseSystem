package com.japps.accountant.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DateUtils {
    companion object{
        public final val DATEFORMAT:String = "yyyy-MM-dd HH:mm:ss"
        public final val DATEFORMAT_from:String = "yyyy-MM-dd 00:00:00"
        public final val DATEFORMAT_to:String = "yyyy-MM-dd 23:59:59"
        public final val CALENDAR_DATEFORMAT:String = "dd-MM-yyyy"

        fun getStringToDate(dateString:String): Date {
            val date = SimpleDateFormat(DATEFORMAT).parse(dateString)
            return date
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getCurrentDateToString(format:String):String{
            val current = Calendar.getInstance()
            val dateFormat = SimpleDateFormat(format);
            val date = dateFormat.format(current.getTime());
            return date
        }
    }
}