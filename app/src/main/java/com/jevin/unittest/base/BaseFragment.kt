package com.jevin.unittest.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

abstract class BaseFragment<V:MvpView,P:MvpPresenter<V>>: MvpFragment<V,P>(){
    protected abstract fun getLayout():Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view:View = inflater.inflate(getLayout(),null)
        view.layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        return view
    }

}