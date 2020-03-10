package me.tienhm.eway_workshop.tracing

import java.util.concurrent.ThreadLocalRandom

class Utils {
    /**
     * Generates a new 64-bit ID, taking care to dodge zero which can be confused with absent
     * Work well with multithreaded by using {@see ThreadLocalRandom}
     *
     * @return: String - unique id (such as: ffe52d34861391c9)
     */
    static String nextId() {
        long random = ThreadLocalRandom.current().nextLong()
        while (random == 0L) {
            random = ThreadLocalRandom.current().nextLong()
        }
        return Long.toHexString(random);
    }
}
