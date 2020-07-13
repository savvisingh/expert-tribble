package com.example.deliveryherotest.repository.db.dao

import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.deliveryherotest.repository.api.model.FilterData

object QueryBuilder {

    fun getFilteredQuery(data: FilterData): SimpleSQLiteQuery{
        val query = "SELECT * FROM restaurantentity ORDER BY ${data.filterBy} ${data.sortDir.toUpperCase()}"
        return SimpleSQLiteQuery(query)
    }
}