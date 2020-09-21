/*
 * Copyright (c) 2020. Deividas Brazauskas
 */

package com.promosapp.stocknews.adapters

import com.promosapp.stocknews.model.ArticleListModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * An interface used for retrofit
 */

interface APIInterface {

//    Get V2 top headlines
    @GET("v2/top-headlines")
    fun fetchTopHeadlines(
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String
    ): Call<ArticleListModel>
}