package com.example.mygithubuserapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bangkit.capstone.database.ChatDao
import com.bangkit.capstone.model.ChatModel

@Database(entities = [ChatModel::class], version = 1)
abstract class ChatRoomDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
    companion object {
        @Volatile
        private var INSTANCE: ChatRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): ChatRoomDatabase {
            if (INSTANCE == null) {
                synchronized(ChatRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        ChatRoomDatabase::class.java, "chat_database")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as ChatRoomDatabase
        }
    }
}