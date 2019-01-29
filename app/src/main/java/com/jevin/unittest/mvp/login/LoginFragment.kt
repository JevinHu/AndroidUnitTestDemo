package com.jevin.unittest.mvp.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.jevin.unittest.R
import com.jevin.unittest.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment:BaseFragment<LoginContract.View,LoginContract.Presenter>(),LoginContract.View {
    override fun getLayout() = R.layout.fragment_login
    override fun createPresenter() = LoginPresenter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.setOnClickListener {
            presenter.clickLogin(edtName.text.toString(),edtPassword.text.toString())
        }
    }

    override fun showErrorNotice(text: String) {
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show()
    }

    override fun showFailResult(text: String) {
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show()
    }

    override fun showSuccessResult(text: String) {
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show()
    }

    override fun showException(text: String) {
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show()
    }

    override fun go2Register() {

    }

    override fun clear() {

    }
}