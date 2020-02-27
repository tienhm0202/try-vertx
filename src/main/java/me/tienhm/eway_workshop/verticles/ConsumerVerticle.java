package me.tienhm.eway_workshop.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import me.tienhm.eway_workshop.controllers.ConsumerController;

public class ConsumerVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    EventBus queue = vertx.eventBus();
    queue.consumer("candidates.add").handler(ConsumerController::addCandidate);

    System.out.println(this.getClass().getName() + " consuming with current deployment id: " + Vertx.currentContext().deploymentID());
  }
}
