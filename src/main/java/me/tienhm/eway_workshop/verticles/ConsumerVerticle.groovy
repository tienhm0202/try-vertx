package me.tienhm.eway_workshop.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus
import me.tienhm.eway_workshop.controllers.ConsumerController
import me.tienhm.eway_workshop.tracing.ConsumerHandler

class ConsumerVerticle extends AbstractVerticle {

    @Override
    void start(Promise<Void> startPromise) throws Exception {
        EventBus queue = vertx.eventBus()

        queue.addInboundInterceptor(ConsumerHandler.create())

        queue.consumer("candidates.add")
                .handler({message -> ConsumerController.addCandidate(message)})

        System.out.println(this.getClass().getName() + " consuming with current deployment id: " + Vertx.currentContext().deploymentID());
    }
}
