package io.devolan.servlet.filters

import org.junit.After
import org.junit.Test
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.RequestDispatcher
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

internal class ForwardFilterTest {

    private val request: HttpServletRequest = Mockito.mock(HttpServletRequest::class.java)
    private val response: ServletResponse = Mockito.mock(ServletResponse::class.java)
    private val chain: FilterChain = Mockito.mock(FilterChain::class.java)
    private val dispatcher: RequestDispatcher = Mockito.mock(RequestDispatcher::class.java)
    private val filter : Filter = ForwardFilter(
        mapOf(
            "/info" to "/actuator/info")
    )

    @After
    fun after() {
        Mockito.clearInvocations<Any>(request, response, chain, dispatcher)
    }

    @Test
    fun `filter when path exist`() {

        // Prepare
        Mockito.`when`(request.requestURI).thenReturn("/info")
        Mockito.`when`(request.getRequestDispatcher(eq("/actuator/info")))
            .thenReturn(dispatcher)

        // execute
        filter.doFilter(request, response, chain)

        //Verify
        Mockito.verify(dispatcher).forward(eq(request), eq(response))
    }

    @Test
    fun `filter when path does not exist`() {

        // Prepare
        Mockito.`when`(request.requestURI).thenReturn("/unknown")

        // execute
        filter.doFilter(request, response, chain)

        //Verify
        Mockito.verify(chain).doFilter(eq(request), eq(response))
    }
}

/*
@Test
fun doFilter() {
    val request = mockk<HttpServletRequest>()
    val response = mockk<HttpServletResponse>()
    val chain = mockk<FilterChain>()
    val dispatcher = mockk<RequestDispatcher>()
    val filter : Filter = io.devolan.servlet.filters.ForwardFilter()

    // Prepare
    every { request.requestURI } returns "/info"
    every { request.getRequestDispatcher("/actuator/info") } returns dispatcher

    // execute
    filter.doFilter(request, response, chain)

    //Verify
    verify {
        dispatcher.forward( eq(request), eq(response))
    }
}

 */
