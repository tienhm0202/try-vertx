package me.tienhm.eway_workshop.tracing.tracers

import io.vertx.ext.web.RoutingContext

class RoutingTracer extends Tracer {
    /**
     * Get trace info (SpanID and TraceID) from context- auto generate if null
     */
    static String getRequestInfo(RoutingContext context, String key) {
        String requestInfo = context.request().headers().get(key)
        if (requestInfo.is(null) || requestInfo.isEmpty()) {
            return nextId()
        }
        return requestInfo
    }
}
