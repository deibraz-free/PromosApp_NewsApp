/**
This class provides various utility functions
**/

package com.promosapp.stocknews.classes

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.promosapp.stocknews.models.articleListModel
import com.promosapp.stocknews.models.article_listitem

class util {
    companion object {
        // get the URL of news articles
        fun getbaseURL(): String {
//            return "http://newsapi.org/v2/everything?domains=wsj.com&apiKey="+constants.Companion.apiKey
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
Scan through the article list array, pick out the needed data
Input: articleListModel class
Output: ArrayList of artcile_listitems
**/
        fun convertToData(data: articleListModel): ArrayList<article_listitem> {
            val list = ArrayList<article_listitem>()

//            Lets first check if its not null, just in case
            for (item in data.articles) {
                print(item)
                list.add(
                    article_listitem(
                        item.title,
                        item.urlToImage,
                        item.description,
                        item.publishedAt
                    )
                )
            }

            return list
        }
    }
}