package com.greektrust.core.interceptor


import com.greektrust.core.network.interceptor.RetryInterceptor
import okhttp3.Request
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import java.io.IOException

class RetryInterceptorTest {

    private fun dummyRequest(): Request =
        Request.Builder()
            .url("https://abcd.com")
            .build()

    @Test
    fun `retries on IOException and succeeds`() {
        val interceptor = RetryInterceptor(
            maxRetries = 3,
            initialDelayMs = 0
        )

        val chain = FakeChain(
            originalRequest = dummyRequest(),
            failWithExceptionTimes = 1,
            successCode = 200
        )

        val response = interceptor.intercept(chain)

        assertEquals(2, chain.proceedCount) // 1 fail + 1 success
        assertEquals(200, response.code)
    }

    @Test
    fun `retries on 5xx response and succeeds`() {
        val interceptor = RetryInterceptor(
            maxRetries = 3,
            initialDelayMs = 0
        )

        val chain = FakeChain(
            originalRequest = dummyRequest(),
            failWithStatusTimes = 2,
            successCode = 200
        )

        val response = interceptor.intercept(chain)

        assertEquals(3, chain.proceedCount) // 2 failures + success
        assertEquals(200, response.code)
    }

    @Test
    fun `stops retrying after max retries and throws exception`() {
        val interceptor = RetryInterceptor(
            maxRetries = 2,
            initialDelayMs = 0
        )

        val chain = FakeChain(
            originalRequest = dummyRequest(),
            failWithExceptionTimes = 5 // always fail
        )

        assertThrows(IOException::class.java) {
            interceptor.intercept(chain)
        }

        assertEquals(2, chain.proceedCount)
    }

    @Test
    fun `does not retry on non-5xx error`() {
        val interceptor = RetryInterceptor(
            maxRetries = 3,
            initialDelayMs = 0
        )

        val chain = FakeChain(
            originalRequest = dummyRequest(),
            successCode = 404
        )

        val response = interceptor.intercept(chain)

        assertEquals(1, chain.proceedCount)
        assertEquals(404, response.code)
    }
}
