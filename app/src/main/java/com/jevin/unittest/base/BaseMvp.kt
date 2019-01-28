package com.jevin.unittest.base

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface BaseMvpView:MvpView{

}

interface BaseMvpPresenter<V:MvpView>:MvpPresenter<V>{

}

interface BaseMvpModel{
    fun attach(presenter:BaseMvpPresenter<BaseMvpView>)

    fun detach()
}