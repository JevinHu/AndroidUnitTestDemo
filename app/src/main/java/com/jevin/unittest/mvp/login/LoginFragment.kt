package com.jevin.unittest.mvp.login

import com.jevin.unittest.R
import com.jevin.unittest.base.BaseFragment

class LoginFragment:BaseFragment<LoginContract.View,LoginContract.Presenter>(),LoginContract.View {
    override fun getLayout() = R.layout.fragment_login
    override fun createPresenter() = LoginPresenter()

    override fun showErrorNotice(text: String) {

    }

    override fun showFailResult(text: String) {

    }

    override fun showSuccessResult(text: String) {

    }

    override fun showException(text: String) {

    }

    override fun go2Register() {

    }

    override fun clear() {

    }
}