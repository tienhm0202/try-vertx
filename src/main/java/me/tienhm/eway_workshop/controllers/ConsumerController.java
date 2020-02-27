package me.tienhm.eway_workshop.controllers;

import io.vertx.core.eventbus.Message;
import me.tienhm.eway_workshop.DB;

public class ConsumerController {
  public static void addCandidate(Message<Object> message) {
    try {
      Thread.sleep(60000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    DB.addCandidate(message.body().toString());
  }
}
