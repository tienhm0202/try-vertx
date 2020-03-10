package me.tienhm.eway_workshop.controllers

import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.RoutingContext;
import me.tienhm.eway_workshop.DB;
import groovy.util.logging.Slf4j
import me.tienhm.eway_workshop.tracing.tracers.RoutingTracer

@Slf4j
class CandidateController {
    static void listCandidates(RoutingContext context) {
        log.warn("Listing candidates")
        context.response().setStatusCode(200).end(DB.listCandidate().toString());
    }

    static void addCandidate(RoutingContext context) {
        log.warn("Adding candidates")
        EventBus queue = context.vertx().eventBus();
        String candidateName = context.request().getFormAttribute("candidate_name");
        if (!DB.getCandidateByName(candidateName).equals("candidate not found")) {
            context.response().setStatusCode(200).end(DB.getCandidateByName(candidateName));
        }
        queue.publish("candidates.add", candidateName, RoutingTracer.genTracingDeliveryOptions());
        context.response().setStatusCode(202).end("accepted");
    }
}
