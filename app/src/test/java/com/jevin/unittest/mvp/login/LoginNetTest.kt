package com.jevin.unittest.mvp.login

import com.google.gson.Gson
import com.jevin.unittest.BaseNetTest
import com.jevin.unittest.bean.LoginBean
import com.jevin.unittest.net.BaseResponse
import com.jevin.unittest.net.NetErrorException
import io.reactivex.subscribers.TestSubscriber
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Assert
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

class LoginNetTest : BaseNetTest<LoginBean>() {
    var model:LoginModel = LoginModel()
    var request = HashMap<String, String>()
//    var test: TestSubscriber<BaseResponse<LoginBean>> = TestSubscriber()

    override fun onBeforeTest() {
        request["name"] = "Jevin"
        request["password"] = "123456"
        model.schedulerProvider = provider
        model.box = box
    }

    override fun onTestSuccess(): OnAssertResult {
        var test: TestSubscriber<BaseResponse<LoginBean>> = TestSubscriber()
        model.login(request["name"]!!, request["password"]!!)
            .subscribe(test)
        return object : OnAssertResult {
            override fun onAssertResult() {
                test.assertNoErrors()
            }
        }
    }

    override fun onTestFail(): OnAssertResult {
        var test: TestSubscriber<BaseResponse<LoginBean>> = TestSubscriber()
//        presenter.retryTimes = 1
        model.retryTimes = 3
        model.login(request["name"]!!, request["password"]!!)
            .subscribe(test)
        return object : OnAssertResult {
            override fun onAssertResult() {
                test.assertError(NetErrorException::class.java)
            }
        }
    }

    override fun onTestBadNet(): OnAssertResult {
        var test: TestSubscriber<BaseResponse<LoginBean>> = TestSubscriber()
        model.retryTimes = 3
        model.login(request["name"]!!, request["password"]!!)
            .subscribe(test)
        return object : OnAssertResult {
            override fun onAssertResult() {
                test.assertError(IOException::class.java)
            }
        }
    }

    override fun onTest404(): OnAssertResult {
        var test: TestSubscriber<BaseResponse<LoginBean>> = TestSubscriber()
        model.retryTimes = 3
        model.login(request["name"]!!, request["password"]!!)
            .subscribe(test)
        return object : OnAssertResult {
            override fun onAssertResult() {
                test.assertError(HttpException::class.java)
            }
        }
    }

    override fun onCheckRequest(request: RecordedRequest) {
        Assert.assertEquals("/test/login", request.path)
        Assert.assertEquals("POST", request.method)
        Assert.assertEquals(Gson().toJson(this.request), request.body.readUtf8())
    }

    override fun setSuccessReponseData(response: BaseResponse<LoginBean>): BaseResponse<LoginBean> {
        response.code = 0
        response.data = LoginBean()
        response.errMsg = ""
        return response
    }

    override fun setFailReponseData(response: BaseResponse<LoginBean>): BaseResponse<LoginBean> {
        response.code = 80010
        response.errMsg = "用户名或密码错误"
        return response
    }

    override fun setBadNetReponseData(mockResponse: MockResponse): MockResponse {
        mockResponse.throttleBody(20,20,TimeUnit.SECONDS)
        return mockResponse
    }

    override fun set404ReponseData(mockResponse: MockResponse): MockResponse {
        mockResponse.setResponseCode(404)
        return mockResponse
    }

    override fun onAfterTest() {

    }

}