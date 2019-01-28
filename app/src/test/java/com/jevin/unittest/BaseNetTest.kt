package com.jevin.unittest

import com.google.gson.Gson
import com.jevin.unittest.net.BaseResponse
import com.startdt.android.uploadfile.worker.http.NetBox
import com.startdt.android.uploadfile.worker.http.TestSchedulerProvider
import io.reactivex.subscribers.TestSubscriber
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.robolectric.shadows.ShadowLog
import java.io.IOException

abstract class BaseNetTest<T> {
    protected var server: MockWebServer? = null

    protected lateinit var box: NetBox

    protected val provider = TestSchedulerProvider()

    protected abstract fun onBeforeTest()

    protected abstract fun onTestSuccess(): OnAssertResult

    protected abstract fun onTestFail(): OnAssertResult

    protected abstract fun onTestBadNet(): OnAssertResult

    protected abstract fun onCheckRequest(request: RecordedRequest)

    protected abstract fun setSuccessReponseData(reponse: BaseResponse<T>): BaseResponse<T>

    protected abstract fun setFailReponseData(reponse: BaseResponse<T>): BaseResponse<T>

    protected abstract fun setBadNetReponseData(mockResponse: MockResponse): MockResponse

    protected abstract fun onAfterTest()

//    @Rule
//    @JvmField
//    var rule = RxJavaTestSchedulerRule()

    @Before
    fun setUp() {
        ShadowLog.stream = System.out

        // 创建一个 MockWebServer
        server = MockWebServer()
        try {
            server!!.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
//        Mockito.mock(NetBox::class.java)
        box = NetBox("http://${server?.hostName}:${server?.port}/")
        onBeforeTest()
    }

    @Test
    fun startTest() {
        enqueueReponse(createReponse(), setSuccessReponseData(BaseResponse<T>()))
        mockApi(onTestSuccess())
        enqueueReponse(createReponse(), setFailReponseData(BaseResponse<T>()))
        mockApi(onTestFail())
        enqueueReponse(setBadNetReponseData(createReponse()), setSuccessReponseData(BaseResponse<T>()))
        onTestBadNet()
        mockApi(onTestBadNet())
    }

    private fun mockApi(assertResult: OnAssertResult) {
        try {
            provider.testScheduler.triggerActions()
            var request = server!!.takeRequest()
            onCheckRequest(request)
            assertResult.onAssertResult()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun enqueueReponse(mockResponse: MockResponse, reponse: BaseResponse<T>) {
        server?.enqueue(mockResponse.setBody(Gson().toJson(reponse)))
    }

    fun createReponse(): MockResponse {
        return MockResponse()
            .addHeader("Content-Type", "application/json;charset=utf-8")
    }

    //    @After
    fun stop() {
        try {
            server!!.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        onAfterTest()
    }

    interface OnAssertResult {
        fun onAssertResult()
    }
}
