/*
 * Copyright (c) 2020. Deividas Brazauskas
 */

package com.promosapp.stocknews

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.promosapp.stocknews.adapters.apiInterface
import com.promosapp.stocknews.adapters.articleListRecyclerAdapter
import com.promosapp.stocknews.classes.constants
import com.promosapp.stocknews.classes.util
import com.promosapp.stocknews.models.article
import com.promosapp.stocknews.models.articleListModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * The main applications activity.
 * It handles the list, data models and applying, parsing, loading data.
 */

class MainActivity : AppCompatActivity() {
    private lateinit var fragment:articleDetailedFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        refreshData()

        swipeRefreshLayout.setOnRefreshListener {
            showProgress()
            refreshData()
            swipeRefreshLayout.isRefreshing = false
        }

        initUI()
    }

//    Hides the detailed view in a fragment form, also hides the background click checker
    private fun fragmentHide() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.setCustomAnimations(R.anim.fade_in_slide, R.anim.fade_out_slide)
        fragmentTransaction.remove(fragment)
        fragmentTransaction.commit()

        fragmentBG.visibility = View.INVISIBLE
    }

    private fun initUI() {
        showProgress()

        fragmentBG.setOnClickListener {
            fragmentHide()
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
                hideProgress()
            }

            override fun onResponse(call: Call<articleListModel>, response: Response<articleListModel>) {
                var data = response.body()
                if (data != null) {
                    var list: List<article> = util.convertToData(data)
                    updateRecyclerView(list)
                }
                hideProgress()
            }
        })
    }

//    Show, hide progress bar
    fun hideProgress() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        progress_Bar.startAnimation(animation)

        Handler().postDelayed({
            progress_Bar.visibility = View.GONE
        }, 250)
    }
    fun showProgress() {
        progress_Bar.visibility = View.VISIBLE
    }

    private fun replaceFragment(data:article) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        val fragmentNew = articleDetailedFragment.newInstance()
        fragmentNew.data = data
        fragment = fragmentNew

        fragmentTransaction.setCustomAnimations(R.anim.fade_in_slide, R.anim.fade_out)
        fragmentTransaction.replace(R.id.fragmentContainer, fragmentNew)
        fragmentTransaction.commit()

        fragmentBG.visibility = View.VISIBLE
        fragmentBG.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))
    }

//    Ran when an item has been clicked, source is articleListRecyclerAdapter
    fun onItemClicked(data:article) {

        replaceFragment(data)
    }

    private fun updateRecyclerView(data: List<article>) {
        util.vibrate(50, baseContext)


//        val animation = AnimationUtils.loadLayoutAnimation(baseContext, R.anim.anim_fall)
//        recyclerView.layoutAnimation(animation)

//        recyclerView.scheduleLayoutAnimation()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = articleListRecyclerAdapter(data)
        }

    }

//    Lets not allow to go back to the launch screen, also hide detailed fragment view if its visible, first
    override fun onBackPressed() {
        if (fragment.isVisible) {
            fragmentHide()
        } else {
            finish()
            System.exit(0)
        }
    }

//    Override the animations
    override fun onResume() {
        super.onResume()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}