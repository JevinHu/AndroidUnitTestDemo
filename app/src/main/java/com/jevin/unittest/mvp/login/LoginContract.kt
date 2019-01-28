package com.jevin.unittest.mvp.login

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface LoginContract {
    interface View:MvpView{
        fun showErrorNotice(text:String)
        fun go2Register()
        fun clear()
    }

    interface Presenter:MvpPresenter<View>{
        fun clickLogin(name:String,password:String)
        fun clickRegister()
    }
}