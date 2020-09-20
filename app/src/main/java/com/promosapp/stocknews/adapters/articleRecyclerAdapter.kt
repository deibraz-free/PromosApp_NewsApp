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
import com.promosapp.stocknews.R
import com.promosapp.stocknews.models.article_listitem
import kotlinx.android.synthetic.main.layout_list_item.view.*

class list_itemReyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<article_listitem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return article_listitemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is article_listitemViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }

    fun submitList(articleList: List<article_listitem>) {
        items = articleList
    }

    class article_listitemViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView) {
        val article_image: ImageView = itemView.article_image
        val article_title: TextView = itemView.article_title
//        val article_time: TextView = itemView.article_time

        fun bind(article:article_listitem) {
            article_title.setText(article.title)
//            article_time.setText(article.datetime)

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide
                .with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(article.image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(article_image)
        }
    }
}