package me.tienhm.eway_workshop.controllers;

import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.RoutingContext;
import me.tienhm.eway_workshop.DB;


public class CandidateController {
  public static void listCandidates(RoutingContext context) {
    context.response().setStatusCode(200).end(DB.listCandidate().toString());
  }

  public static void addCandidate(RoutingContext context) {
    EventBus queue = context.vertx().eventBus();
    String candidateName = context.request().getFormAttribute("candidate_name");
    if (!DB.getCandidateByName(candidateName).equals("candidate not found")) {
      context.response().setStatusCode(200).end(DB.getCandidateByName(candidateName));
    }
    queue.publish("candidates.add", candidateName);
    context.response().setStatusCode(202).end("accepted");
  }

  public static void getCandidate(RoutingContext context) {
    String candidateId = context.request().getParam("candidate_id");
    context.response().setStatusCode(200).end(DB.getCandidate(candidateId));
  }

  public static void updateCandidate(RoutingContext context) {
    String candidateId = context.request().getParam("candidate_id");
    String candidateName = context.request().getFormAttribute("candidate_name");
    context.response().setStatusCode(200).end(DB.updateCandidate(candidateId, candidateName));
  }

  public static void deleteCandidate(RoutingContext context) {
    String candidateId = context.request().getParam("candidate_id");
    context.response().setStatusCode(200).end(DB.deleteCandidate(candidateId));
  }
}
