package com.promosapp.stocknews.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.promosapp.stocknews.LauncherActivity
import com.promosapp.stocknews.MainActivity
import com.promosapp.stocknews.R
import com.promosapp.stocknews.classes.util
import com.promosapp.stocknews.models.article
import com.promosapp.stocknews.models.article_listitem
import kotlinx.android.synthetic.main.layout_list_item.view.*

class RecyclerNew(private val data: List<article_listitem>): RecyclerView.Adapter<RecyclerNew.ViewHolder>() {
    class ViewHolder(item: View):RecyclerView.ViewHolder(item) {
        val article_image: ImageView = item.article_image
        val article_title: TextView = item.article_title
        val article_datetime: TextView = item.article_datetime

        init {
            item.setOnClickListener {
                println("test" + position)
                val intent = Intent(item.context, LauncherActivity::class.java)
                item.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.article_title.text = data[position].title
        holder.article_datetime.text = data[position].datetime

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        Glide
            .with(holder.itemView)
            .applyDefaultRequestOptions(requestOptions)
            .load(data[position].image)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.article_image)

    }
}