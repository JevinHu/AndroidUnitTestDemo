package com.jevin.unittest.mvp.login

import io.reactivex.disposables.CompositeDisposable
import java.io.IOException

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
                        when {
                            it.code == 80003 -> {//用户不存在
                                view.showFailResult("用户不存在")
                            }
                            it.code == 80004 -> {//用户名或密码不正确
                                view.showFailResult("用户名或密码不正确")
                            }
                            else -> {
                                view.showSuccessResult("登录成功")
                            }
                        }
                    }, {
                        when(it){
                            is IOException->{
                                view.showException("网络异常，请检查网络")
                            }
                            else->{
                                view.showException("未知异常")
                            }
                        }
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