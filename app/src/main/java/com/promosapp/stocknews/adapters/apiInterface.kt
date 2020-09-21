/*
 * Copyright (c) 2020. Deividas Brazauskas
 */

package com.promosapp.stocknews.adapters

import com.promosapp.stocknews.models.articleListModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * An interface used for retrofit
 */

interface apiInterface {

//    Get V2 top headlines
    @GET("v2/top-headlines")
    fun fetchTopHeadlines(
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String
    ): Call<articleListModel>
}