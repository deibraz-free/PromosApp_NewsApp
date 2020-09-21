package com.promosapp.stocknews.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.promosapp.stocknews.MainActivity
import com.promosapp.stocknews.R
import com.promosapp.stocknews.models.article_listitem
import kotlinx.android.synthetic.main.layout_list_item.view.*

class articleListRecyclerAdapter(private val data: List<article_listitem>): RecyclerView.Adapter<articleListRecyclerAdapter.ViewHolder>() {
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
        val artcicleCur = data[position]

//        Check when the item has been clicked, when it has, pass the clicked article item
        holder.itemView.setOnClickListener {
            (holder.itemView.context as MainActivity).onItemClicked(artcicleCur)
        }

//        Set the title, date time
        holder.article_title.text = artcicleCur.title
        holder.article_datetime.text = artcicleCur.datetime

//      Handle image setting using Glide
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        Glide
            .with(holder.itemView)
            .applyDefaultRequestOptions(requestOptions)
            .load(artcicleCur.image)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.article_image)
    }
}