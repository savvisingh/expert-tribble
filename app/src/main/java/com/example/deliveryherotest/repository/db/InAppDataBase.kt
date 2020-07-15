package com.example.deliveryherotest.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.deliveryherotest.repository.db.dao.RestaurantDAO
import com.example.deliveryherotest.repository.db.entitiy.RestaurantEntity

@Database(entities = [RestaurantEntity::class], version = 1)
abstract class InAppDataBase : RoomDatabase(){

    abstract fun restaurantDAO(): RestaurantDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: InAppDataBase? = null

        fun getDatabase(context: Context): InAppDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InAppDataBase::class.java,
                    "delivery_hero_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}
