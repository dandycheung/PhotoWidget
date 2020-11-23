package com.qihuan.albumwidget.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.qihuan.albumwidget.bean.WidgetInfo
import com.qihuan.albumwidget.bean.WidgetInfoDao

/**
 * AppDatabase
 * @author qi
 * @since 2020/8/11
 */
@Database(
    entities = [
        WidgetInfo::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun widgetInfoDao(): WidgetInfoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "album_widget"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}