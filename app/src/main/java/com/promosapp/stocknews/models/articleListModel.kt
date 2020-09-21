/*
 * Copyright (c) 2020. Deividas Brazauskas
 */

package com.promosapp.stocknews.models

import com.google.gson.annotations.SerializedName

class articleListModel {
    @SerializedName("status")
    var status: String = ""

    @SerializedName("totalResults")
    var totalResults: Int = 0

    @SerializedName("articles")
    var articles = ArrayList<articleInner>()
}

class articleInner {
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