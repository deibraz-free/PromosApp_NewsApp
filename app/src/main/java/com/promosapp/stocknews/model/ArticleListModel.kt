/*
 * Copyright (c) 2020. Deividas Brazauskas
 */

package com.promosapp.stocknews.model

import com.google.gson.annotations.SerializedName


/**
 * Describes article list model class used by retrofit
 */

class ArticleListModel {
    @SerializedName("status")
    var status: String = ""

    @SerializedName("totalResults")
    var totalResults: Int = 0

    @SerializedName("articles")
    var articles = ArrayList<ArticleInner>()
}

class ArticleInner {
    @SerializedName("author")
    var author: String = ""
    @SerializedName("title")
    var title: String = ""
    @SerializedName("description")
    var description: String = ""
    @SerializedName("url")
    var url: String = ""
    @SerializedName("urlToImage")
    var urlToImage: String = ""
    @SerializedName("publishedAt")
    var publishedAt: String = ""
    @SerializedName("content")
    var content: String = ""
}