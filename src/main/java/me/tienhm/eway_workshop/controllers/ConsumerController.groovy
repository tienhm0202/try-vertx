package me.tienhm.eway_workshop.controllers

import groovy.util.logging.Slf4j;
import io.vertx.core.eventbus.Message;
import me.tienhm.eway_workshop.DB;

@Slf4j
class ConsumerController {
    static void addCandidate(Message<Object> message) {
        log.warn("Consumer adding candidate")
        String candidateName = message.body().toString();
        log.warn("Message: ${candidateName}");
        if (DB.getCandidateByName(candidateName).equals("candidate not found")) {
            DB.addCandidate(candidateName);
        }
    }
}
