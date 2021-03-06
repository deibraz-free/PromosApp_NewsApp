/*
 * Copyright (c) 2020. Deividas Brazauskas
 */

package com.promosapp.stocknews.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.request.RequestOptions
import com.promosapp.stocknews.R
import com.promosapp.stocknews.model.Article
import com.promosapp.stocknews.model.ArticleListModel

/**
 * This class provides various utility functions
 */

class Util {
    companion object {
        // get the URL of news articles
        fun getbaseURL(): String {
//            return "http://newsapi.org/v2/everything?domains=wsj.com&apiKey="+Constants.Companion.apiKey
            return "http://newsapi.org/"
        }

        fun vibrate(vibrationIntensity:Long, context:Context) {
            val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(VibrationEffect.createOneShot(vibrationIntensity, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                vibrator.vibrate(vibrationIntensity)
            }
        }

/**
A simple method to print out some information, mostly used for debugging, only used internally
Input: message : String, current context : Context
Output: None
**/
        fun msgDo(msg: String, curContext: Context, length: Int) {
            Log.d("Toast Message", " " + msg)
            Toast.makeText(curContext, msg, length).show()
        }

/**
A simple method to print out some information, mostly used for debugging, calls msgDo with short length
Input: message : String, current context : Context
Output: None
**/
        fun msg(msg:String, curContext: Context) {
            msgDo(msg, curContext, Toast.LENGTH_SHORT)
        }

/**
A simple method to print out some information, mostly used for debugging, calls msgDo with long length
Input: message : String, current context : Context
Output: None
**/
        fun msgLong(msg:String, curContext: Context) {
            msgDo(msg, curContext, Toast.LENGTH_LONG)
        }

/**
Scan through the Article list array, pick out the needed data
Input: ArticleListModel class
Output: ArrayList of artcile_listitems
**/
        fun convertToData(data: ArticleListModel): ArrayList<Article> {
            val list = ArrayList<Article>()

//            Lets first check if its not null, just in case
            for (item in data.articles) {
                list.add(
                    Article(
                        item.title,
                        item.author,
                        item.description,
                        item.url,
                        item.urlToImage,
                        item.publishedAt,
                        item.content
                    )
                )
            }

            return list
        }

/**
Create default options for glide image loading
Input: None
Output: Glide library RequestOptions
 **/

        fun getGlideDefaultOptions():RequestOptions {
            val ret = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
            return ret
        }
    }
}