package io.devolan.servlet.filters

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class ForwardFilter : Filter {

    private val ENDPOINTS_PATHS = arrayOf(
        "/auditevents",
        "/beans",
        "/caches",
        "/conditions",
        "/configprops",
        "/env",
        "/flyway",
        "/health",
        "/httptrace",
        "/info",
        "/integrationgraph",
        "/loggers",
        "/liquibase",
        "/metrics",
        "/mappings",
        "/scheduledtasks",
        "/sessions",
        "/shutdown",
        "/threaddump",
        "/heapdump",
        "/jolokia",
        "/logfile",
        "/prometheus"
    )

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {

        request as HttpServletRequest

        val path = ENDPOINTS_PATHS.firstOrNull { it == request.requestURI }

        if (path.isNullOrBlank()) {
            chain.doFilter(request, response)
        } else {
            request.getRequestDispatcher("/actuator$path").forward(request, response)
        }
    }
}
