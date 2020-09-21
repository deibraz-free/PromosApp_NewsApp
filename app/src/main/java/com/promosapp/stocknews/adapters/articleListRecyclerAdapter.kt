/*
 * Copyright (c) 2020. Deividas Brazauskas
 */

package com.promosapp.stocknews.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.promosapp.stocknews.MainActivity
import com.promosapp.stocknews.R
import com.promosapp.stocknews.classes.util
import com.promosapp.stocknews.models.article
import kotlinx.android.synthetic.main.layout_list_item.view.*

/**
 * A recycler adapter for handling the main URL list
 */

class articleListRecyclerAdapter(private val data: List<article>): RecyclerView.Adapter<articleListRecyclerAdapter.ViewHolder>() {
    class ViewHolder(item: View):RecyclerView.ViewHolder(item) {

//        Lets init UI elements
        val article_image: ImageView = item.article_image
        val article_title: TextView = item.article_title
        val article_datetime: TextView = item.article_datetime
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Lets store this for future reference
        val articleCur = data[position]

//        Check when the item has been clicked, when it has, pass the clicked article item
        holder.itemView.setOnClickListener {
            (holder.itemView.context as MainActivity).onItemClicked(articleCur)
        }

//        Set the title, date time
        holder.article_title.text = articleCur.title
        holder.article_datetime.text = articleCur.datetime

//      Handle image loading with glide library
        Glide
            .with(holder.itemView)
            .applyDefaultRequestOptions(util.getGlideDefaultOptions())
            .load(articleCur.image)
            .transition(GenericTransitionOptions.with(R.anim.fade_in))
            .into(holder.article_image)
    }
}