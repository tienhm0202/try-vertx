package me.tienhm.eway_workshop.tracing

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
}
