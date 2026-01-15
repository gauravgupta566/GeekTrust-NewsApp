package com.greektrust.core.interceptor

import okhttp3.Call
import okhttp3.Connection
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import java.util.concurrent.TimeUnit


class FakeChain(
    private val originalRequest: Request,
    private val failWithExceptionTimes: Int = 0,
    private val failWithStatusTimes: Int = 0,
    private val successCode: Int = 200
) : Interceptor.Chain {

    lateinit var proceededRequest: Request
    var proceedCount = 0

    override fun request(): Request = originalRequest

    override fun proceed(request: Request): Response {
        proceededRequest = request
        proceedCount++

        // 1️⃣ Simulate IOException failures
        if (proceedCount <= failWithExceptionTimes) {
            throw IOException("Simulated network failure")
        }

        // 2️⃣ Simulate server errors (5xx)
        if (proceedCount <= failWithStatusTimes) {
            return buildResponse(request, 500)
        }

        // 3️⃣ Success response
        return buildResponse(request, successCode)
    }

    private fun buildResponse(
        request: Request,
        code: Int
    ): Response =
        Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(code)
            .message("Response $code")
            .body("{}".toResponseBody())
            .build()

    override fun call(): Call =
        throw UnsupportedOperationException()

    override fun connection(): Connection? = null
    override fun connectTimeoutMillis(): Int = 0
    override fun readTimeoutMillis(): Int = 0
    override fun writeTimeoutMillis(): Int = 0

    override fun withConnectTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain = this
    override fun withReadTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain = this
    override fun withWriteTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain = this
}