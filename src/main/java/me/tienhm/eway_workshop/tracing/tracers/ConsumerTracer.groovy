package me.tienhm.eway_workshop.tracing.tracers

import io.vertx.core.eventbus.DeliveryContext

class ConsumerTracer extends Tracer {
    /**
     * Get trace info (SpanID and TraceID) from context- auto generate if null
     */
    static String getRequestInfo(DeliveryContext context, String key) {
        String requestInfo = context.message().headers().get(key)
        if (requestInfo.is(null) || requestInfo.isEmpty()) {
            return nextId()
        }
        return requestInfo
    }
}
