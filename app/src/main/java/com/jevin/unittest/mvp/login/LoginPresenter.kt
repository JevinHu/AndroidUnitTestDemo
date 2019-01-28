package com.jevin.unittest.mvp.login

import io.reactivex.disposables.CompositeDisposable

class LoginPresenter : LoginContract.Presenter {
    lateinit var view: LoginContract.View
    lateinit var model:LoginContract.Model

    private val compositeDisposable = CompositeDisposable()

    override fun clickLogin(name: String, password: String) {
        when {
            name.isEmpty() -> {
                view.showErrorNotice("请输入用户名")
                return
            }
            password.isEmpty() -> {
                view.showErrorNotice("请输入密码")
                return
            }
            else -> {
                val disposable = model.login(name, password)
                    .subscribe({

                    }, {

                    })
                compositeDisposable.add(disposable)
            }
        }
    }

    override fun clickRegister() {

    }

    override fun destroy() {
        compositeDisposable.clear()
    }

    override fun attachView(view: LoginContract.View) {
        this.view = view
        this.model = LoginModel()
    }

    override fun detachView(retainInstance: Boolean) {
        compositeDisposable.clear()
    }

    override fun detachView() {
        compositeDisposable.clear()
    }
}