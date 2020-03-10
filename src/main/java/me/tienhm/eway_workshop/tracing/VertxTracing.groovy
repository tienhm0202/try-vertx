package me.tienhm.eway_workshop.tracing

import groovy.util.logging.Slf4j
import io.vertx.core.eventbus.Message
import org.slf4j.MDC

@Slf4j
class VertxTracing {
    static final String SPAN_ID_HEADER = "X-B3-SpanId"
    static final String TRACE_ID_HEADER = "X-B3-TraceId"

    static void handle(Message<Object> message) {
        String spanId = getRequestInfo(message, SPAN_ID_HEADER)
        String traceId = getRequestInfo(message, TRACE_ID_HEADER)
        updateMDC(spanId, traceId)
        log.warn("VertxTracing running ...")
        log.warn("${message.headers()}")
    }

    /**
     * Get trace info (SpanID and TraceID) from context- auto generate if null
     */
    static String getRequestInfo(Message<Object> message, String key) {
        String requestInfo = message.headers().get(key)
        if (requestInfo.is(null) || requestInfo.isEmpty()) {
            return Utils.nextId()
        }
        return requestInfo
    }

    static void updateMDC(String spanId, String traceId) {
        // Update MDC logger
        MDC.put(SPAN_ID_HEADER, spanId)
        MDC.put(TRACE_ID_HEADER, traceId)
    }
}
