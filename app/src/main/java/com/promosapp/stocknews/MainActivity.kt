package com.promosapp.stocknews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.promosapp.stocknews.adapters.apiInterface
import com.promosapp.stocknews.adapters.articleListRecyclerAdapter
import com.promosapp.stocknews.adapters.list_itemReyclerAdapter
import com.promosapp.stocknews.classes.constants
import com.promosapp.stocknews.classes.util
import com.promosapp.stocknews.models.articleListModel
import com.promosapp.stocknews.models.article_listitem
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var articleAdapter: list_itemReyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        refreshData()

        swipeRefreshLayout.setOnRefreshListener {
            refreshData()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun refreshData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(constants.baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(apiInterface::class.java)

        api.fetchTopHeadlines(sources = constants.source, apiKey = constants.apiKey).enqueue(object : Callback<articleListModel> {

            override fun onFailure(call: Call<articleListModel>, t: Throwable) {
                util.msg("FAILED", baseContext)
            }

            override fun onResponse(call: Call<articleListModel>, response: Response<articleListModel>) {
                var data = response.body()
                if (data != null) {
                    var list: List<article_listitem> = util.convertToData(data)
                    updateRecyclerView(list)
                }
            }
        })
    }

    private fun updateRecyclerView(data: List<article_listitem>) {
        util.vibrate(50, baseContext)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            articleAdapter = list_itemReyclerAdapter()
            adapter = articleListRecyclerAdapter(data)
        }
    }

//    Lets not allow to go back to the launch screen
    override fun onBackPressed() {
        finish()
        System.exit(0)
    }

//    Override the animations
    override fun onResume() {
        super.onResume()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}