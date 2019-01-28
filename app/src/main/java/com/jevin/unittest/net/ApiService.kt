package com.jevin.unittest.net

import com.jevin.unittest.bean.LoginBean
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("test/login")
    fun login(@Body map:HashMap<String,Any>): Flowable<BaseResponse<LoginBean>>
}