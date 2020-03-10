package me.tienhm.eway_workshop.tracing

import groovy.util.logging.Slf4j
import io.vertx.core.Handler
import io.vertx.core.eventbus.DeliveryContext
import me.tienhm.eway_workshop.tracing.tracers.ConsumerTracer

@Slf4j
class ConsumerHandler implements Handler<DeliveryContext> {
    @Override
    final void handle(DeliveryContext context) {
        String spanId = ConsumerTracer.getRequestInfo(context, ConsumerTracer.SPAN_ID_HEADER)
        String traceId = ConsumerTracer.getRequestInfo(context, ConsumerTracer.TRACE_ID_HEADER)
        ConsumerTracer.updateMDC(spanId, traceId)
        log.warn("ConsumerHandler running ...")
        context.next()
    }

    static ConsumerHandler create() {
        return new ConsumerHandler()
    }
}
