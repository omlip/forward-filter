package io.devolan.servlet.filters

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class ForwardFilter(private val mappings: Map<String, String>) : Filter {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {

        request as HttpServletRequest

        val mapping = mappings.entries.find { it.key == request.requestURI }

        if (mapping == null) {
            chain.doFilter(request, response)
        } else {
            request.getRequestDispatcher(mapping.value).forward(request, response)
        }
    }
}
