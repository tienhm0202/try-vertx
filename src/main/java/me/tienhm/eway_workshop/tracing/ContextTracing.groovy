package me.tienhm.eway_workshop.tracing

import groovy.util.logging.Slf4j
import io.vertx.core.Handler
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.ext.web.RoutingContext


/**
 * Add request tracing via RoutingContext
 */
@Slf4j
class ContextTracing implements Handler<RoutingContext> {

    @Override
    final void handle(RoutingContext context) {
        log.warn("ContextTracing running ...")
        String spanId = getRequestInfo(context, Tracer.SPAN_ID_HEADER)
        String traceId = getRequestInfo(context, Tracer.TRACE_ID_HEADER)
        Tracer.updateMDC(spanId, traceId)

        // Update context metadata, to work with pub - sub architect
        context.put(Tracer.SPAN_ID_HEADER, spanId)
        context.put(Tracer.TRACE_ID_HEADER, traceId)

        // Add TraceID into response header. Should be configurable
        context.response().headers().add(Tracer.TRACE_ID_HEADER, traceId)
        context.next()
    }

    /**
     * Get trace info (SpanID and TraceID) from context- auto generate if null
     */
    private static String getRequestInfo(RoutingContext context, String key) {
        String requestInfo = context.request().headers().get(key)
        if (requestInfo.is(null) || requestInfo.isEmpty()) {
            return Tracer.nextId()
        }
        return requestInfo
    }

    static ContextTracing create() {
        return new ContextTracing()
    }

    final static DeliveryOptions genTracingOptions(RoutingContext context) {
        DeliveryOptions options = new DeliveryOptions()
        options.addHeader(Tracer.SPAN_ID_HEADER, context.get(Tracer.SPAN_ID_HEADER).toString())
        options.addHeader(Tracer.TRACE_ID_HEADER, context.get(Tracer.TRACE_ID_HEADER).toString())
        return options
    }
}
