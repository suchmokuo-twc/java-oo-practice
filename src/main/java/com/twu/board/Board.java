package com.twu.board;

import java.util.*;

public class Board {
    private final Set<Topic> topics = new HashSet<>(8);
    private final Map<Integer, FixedRankingTopic> fixedRankingTopics = new HashMap<>(8);

    public void addTopic(Topic topic) {
        topics.add(topic);
    }

    public void setFixedTopicRanking(Topic topic, int ranking, int bid) {
        if (fixedRankingTopics.containsKey(ranking)) {
            if (fixedRankingTopics.get(ranking).price >= bid) {
                throw new RuntimeException("money not enough");
            }
        }

        // delete previous topic as README said.
        topics.remove(topic);

        fixedRankingTopics.put(ranking, new FixedRankingTopic(topic, bid));
    }

    public void increaseTopicPopularity(Topic topic, int points) {
        topics.stream()
                .filter(t -> t.equals(topic))
                .findFirst()
                .ifPresent(t -> t.increasePopularity(points));
    }

    public List<Topic> getRankingList() {
        int len = topics.size() + fixedRankingTopics.size();
        List<Topic> rankingList = new ArrayList<>(len);

        Iterator<Topic> topicIterator = topics.stream().sorted().iterator();

        for (int i = 1; i <= len; i++) {
            Topic topic = fixedRankingTopics.containsKey(i)
                    ? fixedRankingTopics.get(i).topic
                    : topicIterator.next();

            rankingList.add(topic);
        }

        return rankingList;
    }
}
