package com.greektrust.core.interceptor

import com.greektrust.core.network.interceptor.HeaderInterceptor
import okhttp3.Request
import org.junit.Test
import com.google.common.truth.Truth.assertThat


class HeadingInterceptorTest {

    @Test
    fun `adds User-Agent header`() {
        // GIVEN
        val interceptor = HeaderInterceptor()
        val originalRequest = Request.Builder()
            .url("https://example.com")
            .build()

        val fakeChain = FakeChain(originalRequest)

        // WHEN
        interceptor.intercept(fakeChain)

        // THEN
        val interceptedRequest = fakeChain.proceededRequest

        assertThat(interceptedRequest.header("User-Agent"))
            .isEqualTo("Android")
    }
}