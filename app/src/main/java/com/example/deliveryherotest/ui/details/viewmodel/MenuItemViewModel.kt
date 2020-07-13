package com.example.deliveryherotest.ui.details.viewmodel

import com.example.deliveryherotest.R
import com.example.deliveryherotest.base.BaseViewModel
import com.example.deliveryherotest.repository.api.model.Product

class MenuItemViewModel(val item: Product): BaseViewModel(R.layout.item_menu_item) {
    val name = item.name
    val imageUrl = item.image
    val ingredients = item.ingredients
    val price = "$${item.price}"
}