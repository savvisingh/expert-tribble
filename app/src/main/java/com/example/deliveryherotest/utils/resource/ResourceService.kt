package com.example.deliveryherotest.utils.resource

import android.content.Context
import androidx.core.content.ContextCompat

class ResourceService(val context: Context): IResourceService {

    override fun getColor(res: Int): Int {
        return ContextCompat.getColor(context, res)
    }
}