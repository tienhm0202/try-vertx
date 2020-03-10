package me.tienhm.eway_workshop.tracing.tracers

import io.vertx.core.eventbus.DeliveryOptions
import org.slf4j.MDC

import java.util.concurrent.ThreadLocalRandom

class Tracer {
    static final String SPAN_ID_HEADER = "X-B3-SpanId"
    static final String TRACE_ID_HEADER = "X-B3-TraceId"

    /**
     * Generates a new 64-bit ID, taking care to dodge zero which can be confused with absent
     * Work well with multithreaded by using {@see ThreadLocalRandom}
     *
     * @return: String - unique id (such as: ffe52d34861391c9)
     */
    static String nextId() {
        long random = ThreadLocalRandom.current().nextLong()
        while (random == 0L) {
            random = ThreadLocalRandom.current().nextLong()
        }
        return Long.toHexString(random);
    }

    /**
     * Update MDC Logger - worked with Slf4j
     * @param spanId
     * @param traceId
     */
    static void updateMDC(String spanId, String traceId) {
        // Update MDC logger
        MDC.put(SPAN_ID_HEADER, spanId)
        MDC.put(TRACE_ID_HEADER, traceId)
    }

    /**
     * Generate DeliveryOptions with tracing info. Use to generate message headers before sending to
     * event bus
     * @return
     */
    static DeliveryOptions genTracingDeliveryOptions() {
        DeliveryOptions options = new DeliveryOptions()
        options.addHeader(SPAN_ID_HEADER, getCurrentSpanId())
        options.addHeader(TRACE_ID_HEADER, getCurrentTraceId())
        return options
    }

    static String getCurrentSpanId() {
        return MDC.get(SPAN_ID_HEADER)
    }

    static String getCurrentTraceId() {
        return MDC.get(TRACE_ID_HEADER)
    }
}
