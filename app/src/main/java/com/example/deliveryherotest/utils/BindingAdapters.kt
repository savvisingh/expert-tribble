package com.example.deliveryherotest.utils

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.deliveryherotest.R
import com.example.deliveryherotest.base.BaseViewModel
import com.example.deliveryherotest.base.adapter.BaseRecyclerAdapter

@BindingAdapter("items")
fun setItems(recyclerView: RecyclerView, items: List<BaseViewModel>){
    var adapter = recyclerView.adapter
    if (adapter == null){
        adapter = BaseRecyclerAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    }

    (adapter as BaseRecyclerAdapter).setItems(items)
}

@BindingAdapter("image_url")
fun setImage(imageView: ImageView, url: String){
    if(url.isNotEmpty()){
        Glide.with(imageView)
            .load(url)
            .placeholder(R.drawable.placeholder)
            .into(imageView)
    }else {
        imageView.setBackgroundResource(R.drawable.placeholder)
    }
}

@BindingAdapter("rating")
fun setImage(rating: RatingBar, value: Double){
    rating.rating = value.toFloat()
}

@BindingAdapter("visibility")
fun setVisibility(view: View, value: Boolean){
   view.visibility = if(value)  View.VISIBLE  else View.GONE
}

@BindingAdapter("onClick")
fun setOnClick(view: View, onClickAction: ()->Unit) {
    view.setOnClickListener { v: View? ->
        try {
            onClickAction.invoke()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

@BindingAdapter("textColor")
fun setTextColor(view: TextView, @ColorRes id: Int){
    try {
        view.setTextColor(view.resources.getColor(id))
    }catch (e: Exception) {
        e.printStackTrace()
    }
}



