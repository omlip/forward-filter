package io.devolan.servlet.filters

import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.RequestDispatcher
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

internal class ForwardFilterTest {

    private val request = mockk<HttpServletRequest>()
    private val response = mockk<ServletResponse>()
    private val chain = mockk<FilterChain>()
    private val dispatcher = mockk<RequestDispatcher>()

    private val filter: Filter = ForwardFilter(
        mapOf(
            "/info" to "/actuator/info"
        )
    )

    @AfterEach
    fun after() {
        clearMocks(request, response, chain, dispatcher)
    }

    @Test
    fun `filter when path exist`() {
        // Prepare
        every { request.requestURI } returns "/info"
        every { request.getRequestDispatcher("/actuator/info") } returns dispatcher
        justRun { dispatcher.forward(request, response) }

        // execute
        filter.doFilter(request, response, chain)

        //Verify
        verify {
            dispatcher.forward(request, response)
        }
    }

    @Test
    fun `filter when path does not exist`() {

        // Prepare
        every { request.requestURI } returns "/unknown"
        justRun { chain.doFilter(request, response) }

        // execute
        filter.doFilter(request, response, chain)

        //Verify
        verify {
            chain.doFilter(request, response)
        }
    }

}
