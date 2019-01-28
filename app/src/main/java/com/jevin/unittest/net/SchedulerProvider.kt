package com.startdt.android.uploadfile.worker.http

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler

interface SchedulerProvider {
    fun uiScheduler() : Scheduler
    fun ioScheduler() : Scheduler
}

class AppSchedulerProvider : SchedulerProvider {
    override fun ioScheduler() = Schedulers.io()
    override fun uiScheduler(): Scheduler = AndroidSchedulers.mainThread()
}

class TestSchedulerProvider : SchedulerProvider {

    val testScheduler: TestScheduler = TestScheduler()

    override fun uiScheduler() = testScheduler
    override fun ioScheduler() = testScheduler
}