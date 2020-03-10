package me.tienhm.eway_workshop.tracing

import groovy.util.logging.Slf4j
import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext
import me.tienhm.eway_workshop.tracing.tracers.RoutingTracer


/**
 * Add request tracing via RoutingContext
 */
@Slf4j
class RoutingHandler implements Handler<RoutingContext> {

    @Override
    final void handle(RoutingContext context) {
        log.warn("RoutingHandler running ...")
        String spanId = RoutingTracer.getRequestInfo(context, RoutingTracer.SPAN_ID_HEADER)
        String traceId = RoutingTracer.getRequestInfo(context, RoutingTracer.TRACE_ID_HEADER)
        RoutingTracer.updateMDC(spanId, traceId)

        // Update context metadata, to work with pub - sub architect
        context.put(RoutingTracer.SPAN_ID_HEADER, spanId)
        context.put(RoutingTracer.TRACE_ID_HEADER, traceId)

        // Add TraceID into response header. Should be configurable
        context.response().headers().add(RoutingTracer.TRACE_ID_HEADER, traceId)
        context.next()
    }

    static RoutingHandler create() {
        return new RoutingHandler()
    }
}
