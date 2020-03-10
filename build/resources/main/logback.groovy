import ch.qos.logback.classic.PatternLayout

statusListener(NopStatusListener)
appender("STDOUT", ConsoleAppender) {
    layout(PatternLayout) {
        pattern = "%d{HH:mm:ss.SSS} [%X{X-B3-TraceId}-%X{X-B3-SpanId}] [%thread] %-5level %logger{100} - %msg%n"
    }
}
logger("io.vertx.ext.web.handler.impl.LoggerHandlerImpl", ALL, ["Async"], false)
logger("io.netty", WARN)
logger("io.vertx", WARN)

root(WARN, ["STDOUT"])