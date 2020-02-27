package me.tienhm.eway_workshop.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import me.tienhm.eway_workshop.controllers.CandidateController;
import me.tienhm.eway_workshop.controllers.VoteController;

public class VoteVerticle extends AbstractVerticle {
  private HttpServer server;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    router.route(HttpMethod.GET, "/candidates/").handler(CandidateController::listCandidates);
    router.route(HttpMethod.POST, "/votes/:candidate_id").handler(VoteController::vote);

    server = vertx.createHttpServer().requestHandler(router).listen(8889);

    System.out.println(this.getClass().getName() + " port: 8889 started with current deployment id: " + Vertx.currentContext().deploymentID());
  }
}
