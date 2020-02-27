package me.tienhm.eway_workshop.controllers;

import io.vertx.core.eventbus.Message;
import me.tienhm.eway_workshop.DB;

public class ConsumerController {
  public static void addCandidate(Message<Object> message) {
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    String candidateName = message.body().toString();
    System.out.println("Message: " + candidateName);
    if (DB.getCandidateByName(candidateName).equals("candidate not found")) {
      DB.addCandidate(candidateName);
    }
  }
}
