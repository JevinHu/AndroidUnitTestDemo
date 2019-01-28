package com.jevin.unittest.mvp.login

import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

class LoginPresenterTest{
    var presenter = LoginPresenter()
    var server:MockWebServer = MockWebServer()

    @Before
    fun before(){
        try {
            server!!.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun testLogin(){
        presenter.login("maomao","dashazi")
            .subscribe({

            },{

            })
    }
}