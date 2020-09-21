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
import com.promosapp.stocknews.R
import com.promosapp.stocknews.model.Article
import com.promosapp.stocknews.ui.articles.ArticlesActivity
import com.promosapp.stocknews.utils.Util
import kotlinx.android.synthetic.main.layout_list_item.view.*

/**
 * A recycler adapter for handling the main URL list
 */

class ArticleListRecyclerAdapter(private val data: List<Article>): RecyclerView.Adapter<ArticleListRecyclerAdapter.ViewHolder>() {
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

//        Check when the item has been clicked, when it has, pass the clicked Article item
        holder.itemView.setOnClickListener {
            Util.vibrate(50, holder.itemView.context)
            (holder.itemView.context as ArticlesActivity).onItemClicked(articleCur)
        }

//        Set the title, date time
        holder.article_title.text = articleCur.title
        holder.article_datetime.text = articleCur.datetime

//      Handle image loading with glide library
        Glide
            .with(holder.itemView)
            .applyDefaultRequestOptions(Util.getGlideDefaultOptions())
            .load(articleCur.image)
            .transition(GenericTransitionOptions.with(R.anim.fade_in))
            .into(holder.article_image)
    }
}