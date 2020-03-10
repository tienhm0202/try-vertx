package me.tienhm.eway_workshop.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import me.tienhm.eway_workshop.controllers.CandidateController
import me.tienhm.eway_workshop.tracing.ContextTracing

class AdminVerticle extends AbstractVerticle {
    private HttpServer server

    @Override
    void start(Promise<Void> startPromise) throws Exception {
        Router router = Router.router(vertx)
        router.route().handler(BodyHandler.create())
        router.route().handler(ContextTracing.create())

        // Candidates
        router.get("/candidates/").handler({context -> CandidateController.listCandidates(context)})
        router.post("/candidates/").handler({context -> CandidateController.addCandidate(context)})

        server = vertx.createHttpServer().requestHandler(router).listen(8888);

        System.out.println(this.getClass().getName() + " port: 8888 started with current deployment id: " + Vertx.currentContext().deploymentID());
    }
}
