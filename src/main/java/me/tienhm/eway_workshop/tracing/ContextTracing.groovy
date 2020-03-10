package me.tienhm.eway_workshop.tracing

import groovy.util.logging.Slf4j
import io.vertx.core.Handler
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.ext.web.RoutingContext
import org.slf4j.MDC


/**
 *
 */
@Slf4j
class ContextTracing implements Handler<RoutingContext> {
    static final String SPAN_ID_HEADER = "X-B3-SpanId"
    static final String TRACE_ID_HEADER = "X-B3-TraceId"

    @Override
    final void handle(RoutingContext context) {
        log.warn("ContextTracing running ...")
        String spanId = getRequestInfo(context, SPAN_ID_HEADER)
        String traceId = getRequestInfo(context, TRACE_ID_HEADER)
        updateMDC(spanId, traceId)

        // Update context metadata, to work with pub - sub architect
        context.put(SPAN_ID_HEADER, spanId)
        context.put(TRACE_ID_HEADER, traceId)
        context.response().headers().add(TRACE_ID_HEADER, traceId)
        context.next()
    }

    /**
     * Get trace info (SpanID and TraceID) from context- auto generate if null
     */
    private static String getRequestInfo(RoutingContext context, String key) {
        String requestInfo = context.request().headers().get(key)
        if (requestInfo.is(null) || requestInfo.isEmpty()) {
            return Utils.nextId()
        }
        return requestInfo
    }

    static ContextTracing create() {
        return new ContextTracing()
    }

    private static void updateMDC(String spanId, String traceId) {
        // Update MDC logger
        MDC.put(SPAN_ID_HEADER, spanId)
        MDC.put(TRACE_ID_HEADER, traceId)
    }

    final static DeliveryOptions genTracingOptions(RoutingContext context) {
        DeliveryOptions options = new DeliveryOptions()
        options.addHeader(SPAN_ID_HEADER, context.get(SPAN_ID_HEADER).toString())
        options.addHeader(TRACE_ID_HEADER, context.get(TRACE_ID_HEADER).toString())
        return options
    }
}
