package com.example.whattoeat2.view

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.whattoeat2.model.Name

@Database(entities = [Name::class], version = 1, exportSchema = false)
abstract class PostsDatabase : RoomDatabase() {
    abstract fun postsDao(): PostsDao

    companion object {
        @Volatile
        private var instance: PostsDatabase? = null
        @Synchronized
        fun getInstance(context: Context): PostsDatabase? {
            val tempInstance = instance
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PostsDatabase::class.java,
                    "posts_table"
                ).build()
                return instance

            }
//            if (instance == null) {
//                instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    PostsDatabase::class.java, "posts_database"
//                )
//                    .fallbackToDestructiveMigration()
//                    .build()
//            }
//            return instance
        }
    }
}

