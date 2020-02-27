package me.tienhm.eway_workshop;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import me.tienhm.eway_workshop.verticles.AdminVerticle;
import me.tienhm.eway_workshop.verticles.ConsumerVerticle;
import me.tienhm.eway_workshop.verticles.VoteVerticle;

public class Runner {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(AdminVerticle.class.getName(), new DeploymentOptions().setInstances(2));
    vertx.deployVerticle(VoteVerticle.class.getName(), new DeploymentOptions().setInstances(2));
    vertx.deployVerticle(ConsumerVerticle.class.getName(), new DeploymentOptions().setInstances(2));
  }
}
