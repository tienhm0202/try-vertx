package me.tienhm.eway_workshop;

public class DB {
    private static Map<String, String> CANDIDATES = new HashMap<String, String>();
    private static Map<String, Integer> VOTES = new HashMap<String, Integer>();

    public static String addCandidate(String candidateName) {
        if (!CANDIDATES.containsValue(candidateName)) {
            UUID uuid = UUID.randomUUID();
            CANDIDATES.put(uuid.toString(), candidateName);
            return uuid.toString();
        } else {
            return getKeyByValue(CANDIDATES, candidateName);
        }
    }

    private static String getKeyByValue(Map<String, String> map, String value) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return "candidate not found";
    }

    static Map<String, String> listCandidate() {
        return CANDIDATES;
    }

    static String getCandidate(String candidateId) {
        return CANDIDATES.getOrDefault(candidateId, "candidate not found");
    }

    static String getCandidateByName(String name) {
        return getKeyByValue(CANDIDATES, name);
    }

    static String updateCandidate(String candidateId, String candidateName) {
        CANDIDATES.put(candidateId, candidateName);
        return candidateName;
    }

    static String deleteCandidate(String candidateId) {
        CANDIDATES.remove(candidateId);
        VOTES.remove(candidateId);
        return candidateId;
    }

    static Integer vote(String candidateId) {
        if(!CANDIDATES.containsKey(candidateId)) {
            return 0;
        }
        if (VOTES.containsKey(candidateId)) {
            Integer vote = VOTES.get(candidateId) + 1;
            VOTES.put(candidateId, vote);
            return vote;
        } else {
            VOTES.put(candidateId, 1);
            return 1;
        }
    }

    static Map<String, Integer> getResult() {
        return VOTES;
    }
}
