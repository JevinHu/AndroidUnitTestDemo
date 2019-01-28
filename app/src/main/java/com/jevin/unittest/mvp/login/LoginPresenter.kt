package com.jevin.unittest.mvp.login

import com.jevin.unittest.BuildConfig
import com.jevin.unittest.base.BasePresenter
import com.jevin.unittest.bean.LoginBean
import com.jevin.unittest.net.ApiService
import com.jevin.unittest.net.BaseResponse
import com.jevin.unittest.net.NetErrorException
import com.startdt.android.uploadfile.worker.http.NetBox
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Predicate
import java.io.IOException
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

class LoginPresenter :BasePresenter(), LoginContract.Presenter {
    lateinit var view: LoginContract.View
    var box: NetBox = NetBox(BuildConfig.APP_HOST)
    var retryTimes = 0

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
                retryTimes = 0
                val disposable = login(name, password)
                    .subscribe({

                    },{

                    })
                compositeDisposable.add(disposable)
            }
        }
    }

    override fun clickRegister() {

    }

    override fun destroy() {

    }

    override fun attachView(view: LoginContract.View) {
        this.view = view
    }

    override fun detachView(retainInstance: Boolean) {

    }

    override fun detachView() {

    }

    fun login(name: String, password: String): Flowable<BaseResponse<LoginBean>> {
        return Flowable.just("")
            .subscribeOn(schedulerProvider.ioScheduler())
            .map {
                val map = HashMap<String, Any>()
                map["name"] = name
                map["password"] = password
                return@map map
            }
            .flatMap {
                retryTimes++
                return@flatMap box.getService(ApiService::class.java).login(it)
            }
            .filter(Predicate {
                if (it.code != 0) {
                    throw NetErrorException()
                } else {
                    return@Predicate true
                }
            })
            .retryWhen {
                return@retryWhen it.flatMap {
                    when (it) {
                        is NetErrorException -> {
                            if (retryTimes < 4) {
                                return@flatMap Flowable.timer(1, TimeUnit.SECONDS)
                            } else {
                                return@flatMap Flowable.error<Throwable>(it)
                            }
                        }
                        is IOException -> {
                            if (retryTimes < 4) {
                                return@flatMap Flowable.timer(1, TimeUnit.SECONDS)
                            } else {
                                return@flatMap Flowable.error<Throwable>(it)
                            }
                        }
                        else -> return@flatMap Flowable.error<Throwable>(it)
                    }
                }
            }
            .observeOn(schedulerProvider.uiScheduler())
    }
}