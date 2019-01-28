package com.jevin.unittest

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RxJavaTestSchedulerRule :TestRule {
    val mTestSchedulers = TestScheduler()

    override fun apply(base: Statement?, description: Description?): Statement {
        return object :Statement(){
            override fun evaluate() {
                RxJavaPlugins.setIoSchedulerHandler { mTestSchedulers }
                RxJavaPlugins.setNewThreadSchedulerHandler { mTestSchedulers }
                RxJavaPlugins.setComputationSchedulerHandler { mTestSchedulers }
                RxAndroidPlugins.setMainThreadSchedulerHandler { mTestSchedulers }

                try {
                    base!!.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}