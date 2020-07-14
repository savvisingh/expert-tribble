package com.example.deliveryherotest.repository.db.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.deliveryherotest.repository.db.entitiy.RestaurantEntity
import io.reactivex.Flowable

@Dao
interface RestaurantDAO {

    @Query("SELECT * FROM restaurantentity")
    fun getAll(): List<RestaurantEntity>

    @Query("SELECT * FROM restaurantentity WHERE id = :id")
    fun getRestaurant(id: Int): RestaurantEntity?

    @Query("SELECT * FROM restaurantentity WHERE isFavourite = 1")
    fun getAllFavourite(): List<RestaurantEntity>

    @Query("SELECT * FROM restaurantentity WHERE id = :id")
    fun getRestaurantFlow(id: Int): Flowable<RestaurantEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(restaurant: RestaurantEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<RestaurantEntity?>)

    @Update
    fun update(restaurant: RestaurantEntity)

    @RawQuery(observedEntities = [RestaurantEntity::class])
    fun getFilteredList(query: SupportSQLiteQuery): Flowable<List<RestaurantEntity>>

}