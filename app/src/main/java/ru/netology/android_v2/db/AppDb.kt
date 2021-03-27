package ru.netology.android_v2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.netology.android_v2.dao.PostDao
import ru.netology.android_v2.entry.PostEntity

@Database(entities = [PostEntity::class], version = 2)
abstract class AppDb : RoomDatabase() {
    abstract fun postDao(): PostDao

    companion object {
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it}
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
                context,
                AppDb::class.java,
                "app.db"
        ).addMigrations(object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE posts ADD COLUMN authorAvatar TEXT DEFAULT '' NOT NULL");
            }
        })
                .allowMainThreadQueries().build()
    }
}

