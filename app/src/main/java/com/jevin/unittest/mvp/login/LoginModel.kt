package com.jevin.unittest.mvp.login

import com.jevin.unittest.BuildConfig
import com.jevin.unittest.base.BaseMvpPresenter
import com.jevin.unittest.base.BaseMvpView
import com.jevin.unittest.bean.LoginBean
import com.jevin.unittest.net.ApiService
import com.jevin.unittest.net.BaseResponse
import com.jevin.unittest.net.NetErrorException
import com.startdt.android.uploadfile.worker.http.AppSchedulerProvider
import com.startdt.android.uploadfile.worker.http.NetBox
import com.startdt.android.uploadfile.worker.http.SchedulerProvider
import io.reactivex.Flowable
import io.reactivex.functions.Predicate
import java.io.IOException
import java.util.concurrent.TimeUnit

class LoginModel :LoginContract.Model{
    var schedulerProvider: SchedulerProvider = AppSchedulerProvider()
    var retryTimes = 0
    var box: NetBox = NetBox(BuildConfig.APP_HOST)

    override fun login(name: String, password: String): Flowable<BaseResponse<LoginBean>> {
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

    override fun attach(presenter: BaseMvpPresenter<BaseMvpView>) {

    }

    override fun detach() {
    }
}