package com.jevin.unittest.base

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.startdt.android.uploadfile.worker.http.AppSchedulerProvider
import com.startdt.android.uploadfile.worker.http.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter {
    var schedulerProvider: SchedulerProvider = AppSchedulerProvider()
    val compositeDisposable = CompositeDisposable()
}