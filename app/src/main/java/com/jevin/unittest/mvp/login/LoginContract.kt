package com.jevin.unittest.mvp.login

import com.jevin.unittest.base.BaseMvpModel
import com.jevin.unittest.base.BaseMvpPresenter
import com.jevin.unittest.base.BaseMvpView
import com.jevin.unittest.bean.LoginBean
import com.jevin.unittest.net.BaseResponse
import io.reactivex.Flowable

interface LoginContract {
    interface View:BaseMvpView{
        fun showErrorNotice(text:String)
        fun go2Register()
        fun clear()
    }

    interface Presenter:BaseMvpPresenter<View>{
        fun clickLogin(name:String,password:String)
        fun clickRegister()
    }

    interface Model:BaseMvpModel{
        fun login(name: String,password: String): Flowable<BaseResponse<LoginBean>>
    }
}