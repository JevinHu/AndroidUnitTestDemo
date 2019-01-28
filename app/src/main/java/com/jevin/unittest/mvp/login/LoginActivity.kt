package com.jevin.unittest.mvp.login

import com.jevin.unittest.base.BaseActivity

class LoginActivity:BaseActivity() {
    override fun getLayout()=0

    override fun createView() = LoginFragment()
}