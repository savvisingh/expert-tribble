package com.example.deliveryherotest.repository.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.deliveryherotest.repository.db.entitiy.RestaurantEntity
import io.reactivex.Flowable

@Dao
interface RestaurantDAO {

    @Query("SELECT * FROM restaurantentity")
    fun getAll(): List<RestaurantEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(restaurant: RestaurantEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<RestaurantEntity?>)

}