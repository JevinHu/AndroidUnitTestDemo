package com.jevin.unittest.mvp.login

import com.jevin.unittest.bean.LoginBean
import com.jevin.unittest.net.BaseResponse
import io.reactivex.Flowable
import org.junit.Test
import org.mockito.Mockito

class LoginPresenterTest {
    var presenter = LoginPresenter()
    var mockView = Mockito.mock(LoginContract.View::class.java)
    var mockModel = Mockito.mock(LoginContract.Model::class.java)

    init {
        presenter.attachView(mockView)
        presenter.model = mockModel
    }

    @Test
    fun testClickLogin() {
        presenter.clickLogin("", "")
        Mockito.verify(mockView).showErrorNotice("请输入用户名")

        presenter.clickLogin("Jevin", "")
        Mockito.verify(mockView).showErrorNotice("请输入密码")

        var reponse = BaseResponse<LoginBean>()
        reponse.data = LoginBean()
        Mockito.`when`(mockModel.login(Mockito.anyString(),Mockito.anyString())).thenReturn(Flowable.just(reponse))
        presenter.clickLogin("Jevin", "123456")
        Mockito.verify(mockModel).login("Jevin", "123456")
    }
}