package me.tienhm.eway_workshop.controllers;

import io.vertx.ext.web.RoutingContext;
import me.tienhm.eway_workshop.DB;

public class VoteController {
  public static void getResult(RoutingContext context) {
    context.response().setStatusCode(200).end(DB.getResult().toString());
  }

  public static void vote(RoutingContext context) {
    String candidateId = context.request().getParam("candidate_id");
    context.response().setStatusCode(200).end(DB.vote(candidateId).toString());
  }
}
