/*
 * Copyright (c) 2020. Deividas Brazauskas
 */

package com.promosapp.stocknews

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.promosapp.stocknews.classes.util
import com.promosapp.stocknews.models.article
import kotlinx.android.synthetic.main.fragment_articledetail.*

/**
 * A detailed article view fragment class.
 * Main purpose of it is to set up view and apply the data to it its sub views.
 */

class articleDetailedFragment : Fragment() {
    lateinit var data:article

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_articledetail, container, false)
    }

//    This runs after everything has loaded
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        Set data to the view
        frag_article_title.text = data.title
        frag_article_description.text = data.description
//        frag_article_content.text = data.content
        frag_article_author.text = data.author
        frag_article_datetime.text = data.datetime

//        Load the image with glide
        Glide
            .with(this)
            .applyDefaultRequestOptions(util.getGlideDefaultOptions())
            .load(data.image)
            .transition(GenericTransitionOptions.with(R.anim.fade_in))
            .into(frag_article_image)

//        Adds an onclick listener to the read more button
        frag_button_readmore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(data.url)
            startActivity(intent)
        }
    }


    companion object {
        fun newInstance() = articleDetailedFragment()
    }
}