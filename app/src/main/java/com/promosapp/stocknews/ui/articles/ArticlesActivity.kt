/*
 * Copyright (c) 2020. Deividas Brazauskas
 */

package com.promosapp.stocknews.ui.articles

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.promosapp.stocknews.R
import com.promosapp.stocknews.adapters.APIInterface
import com.promosapp.stocknews.adapters.ArticleListRecyclerAdapter
import com.promosapp.stocknews.model.Article
import com.promosapp.stocknews.model.ArticleDetailedFragment
import com.promosapp.stocknews.model.ArticleListModel
import com.promosapp.stocknews.utils.Constants
import com.promosapp.stocknews.utils.Util
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

//class ArticlesActivity : AppCompatActivity(), ArticlesView {
class ArticlesActivity : AppCompatActivity() {
    private lateinit var fragment: ArticleDetailedFragment
//    private val presenter = ArticlesPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        presenter.setView(this)
        refreshData()

//        Init
        initUI()
    }

//    Hides the detailed view in a fragment form, also hides the background click checker
    private fun hideFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.setCustomAnimations(
            R.anim.fade_in_slide,
            R.anim.fade_out_slide
        )
        fragmentTransaction.remove(fragment)
        fragmentTransaction.commit()

        fragmentBG.visibility = View.INVISIBLE
    }

//    Initialize UI
    private fun initUI() {

//      Handle swipe down - refresh
        swipeRefreshLayout.setOnRefreshListener {
            Util.vibrate(50, baseContext)

            showProgress()
            refreshData()
            swipeRefreshLayout.isRefreshing = false
        }

        showProgress()

//      Listen for taps from article detailed fragment view, vibrate, hide fragment
        fragmentBG.setOnClickListener {
            Util.vibrate(50, baseContext)
            hideFragment()
        }
    }

//    Contacts the URL to fetch new data using retrofit2
    private fun refreshData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(APIInterface::class.java)
        api.fetchTopHeadlines(sources = Constants.source, apiKey = Constants.apiKey).enqueue(object : Callback<ArticleListModel> {

//            Handle failure state
            override fun onFailure(call: Call<ArticleListModel>, t: Throwable) {
                Util.msg(getString(R.string.fetching_failed), baseContext)
                hideProgress()
            }

//            Handle success state
            override fun onResponse(call: Call<ArticleListModel>, response: Response<ArticleListModel>) {
                var data = response.body()
                if (data != null) {
                    var list: List<Article> = Util.convertToData(data)
                    updateRecyclerView(list)
                }
                hideProgress()
            }
        })
    }

    //    Perform recylcer view update with new data
    private fun updateRecyclerView(data: List<Article>) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ArticlesActivity)
            adapter = ArticleListRecyclerAdapter(data)
        }

    }

//    Show, hide progress bar
    fun hideProgress() {
        val animation = AnimationUtils.loadAnimation(this,
            R.anim.fade_out
        )
        progress_Bar.startAnimation(animation)

        Handler().postDelayed({
            progress_Bar.visibility = View.GONE
        }, 250)
    }
    fun showProgress() {
        progress_Bar.visibility = View.VISIBLE
    }

//    This method will create a new fragment, replace the older one that will display article detailed view
    private fun replaceFragment(data: Article) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        val fragmentNew =
            ArticleDetailedFragment.newInstance()
        fragmentNew.data = data
        fragment = fragmentNew

        fragmentTransaction.setCustomAnimations(
            R.anim.fade_in_slide,
            R.anim.fade_out
        )
        fragmentTransaction.replace(R.id.fragmentContainer, fragmentNew)
        fragmentTransaction.commit()

        fragmentBG.visibility = View.VISIBLE
        fragmentBG.startAnimation(AnimationUtils.loadAnimation(this,
            R.anim.fade_in
        ))
    }

//    Ran when an item has been clicked, source is ArticleListRecyclerAdapter
    fun onItemClicked(data: Article) {
        replaceFragment(data)
    }


//    Lets not allow to go back to the launch screen, also hide detailed fragment view if its visible, first
    override fun onBackPressed() {
        if (fragment.isVisible) {
            hideFragment()
        } else {
            finish()
            System.exit(0)
        }
    }

//    Override the animations
    override fun onResume() {
        super.onResume()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
//        presenter.onResume()
    }
}