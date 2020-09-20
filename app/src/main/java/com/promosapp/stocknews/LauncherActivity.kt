package com.promosapp.stocknews

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.promosapp.stocknews.classes.util
import kotlinx.android.synthetic.main.activity_launcher.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
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