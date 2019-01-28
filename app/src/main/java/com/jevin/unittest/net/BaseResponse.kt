package com.jevin.unittest.net

class BaseResponse<T> {
    var code:Int=0
    var errMsg:String=""
    var data:T?=null
}