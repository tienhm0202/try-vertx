package me.tienhm.eway_workshop.tracing

import groovy.util.logging.Slf4j
import io.vertx.core.Handler
import io.vertx.core.eventbus.DeliveryContext

@Slf4j
class VertxTracing implements Handler<DeliveryContext> {
    @Override
    final void handle(DeliveryContext context) {
        String spanId = getRequestInfo(context, Tracer.SPAN_ID_HEADER)
        String traceId = getRequestInfo(context, Tracer.TRACE_ID_HEADER)
        Tracer.updateMDC(spanId, traceId)
        log.warn("VertxTracing running ...")
        context.next()
    }

    /**
     * Get trace info (SpanID and TraceID) from context- auto generate if null
     */
    static String getRequestInfo(DeliveryContext context, String key) {
        String requestInfo = context.message().headers().get(key)
        if (requestInfo.is(null) || requestInfo.isEmpty()) {
            return Tracer.nextId()
        }
        return requestInfo
    }

    static VertxTracing create() {
        return new VertxTracing()
    }
}
