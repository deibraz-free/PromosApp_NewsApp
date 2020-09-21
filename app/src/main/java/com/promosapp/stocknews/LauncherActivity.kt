/*
 * Copyright (c) 2020. Deividas Brazauskas
 */

package com.promosapp.stocknews

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.promosapp.stocknews.classes.util
import kotlinx.android.synthetic.main.activity_launcher.*

/**
 * The first actvity - splash screen launcher.
 * It auto deactivates after some time. Can also be deactivated by tapping the screen.
 */

class LauncherActivity : AppCompatActivity() {
    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_launcher)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        mRunnable = Runnable {
            endSplash()
        }

        mHandler = Handler()

        mHandler.postDelayed(mRunnable, 3000)

        initUI()
    }

    private fun endSplash() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent)
        finish()

        util.vibrate(125, baseContext)
    }

    private fun initUI() {
        logo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))

        button_bg.setOnClickListener {
            mHandler.removeCallbacks(mRunnable)

            endSplash()
        }
    }

    //    Override the animations
    override fun onResume() {
        super.onResume()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}