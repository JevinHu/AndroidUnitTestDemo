package com.jevin.unittest.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import com.jevin.unittest.R

abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun getLayout(): Int
    protected abstract fun createView(): Fragment
    protected var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        var layout = getLayout()
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(if (layout == 0) R.layout.activity_base else layout)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        if (null == fragment)
            onCreateView()
    }

    fun onCreateView() {
        if (null == findViewById(R.id.parent)) {
            return
        }
        fragment = createView()
        supportFragmentManager.beginTransaction().add(R.id.parent, fragment).commit()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}