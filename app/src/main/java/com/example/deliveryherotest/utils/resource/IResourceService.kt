package com.example.deliveryherotest.utils.resource

import androidx.annotation.ColorRes

interface IResourceService {
    fun getColor(@ColorRes res: Int): Int
}