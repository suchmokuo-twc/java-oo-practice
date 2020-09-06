package com.twu.board;

import java.util.*;

public class Board {
    private final Set<Topic> topics = new HashSet<>(8);
    private final Map<Integer, Topic> fixedRankingTopics = new HashMap<>(8);

    public void addTopic(Topic topic) {
        topics.add(topic);
    }

    public void setFixedTopicRanking(Topic topic, int ranking) {
        if (ranking < 1 || ranking > topics.size()) {
            throw new RuntimeException("range error");
        }

        Topic oldFixedRankingTopic = fixedRankingTopics.get(ranking);

        if (oldFixedRankingTopic != null) {
            if (oldFixedRankingTopic.getCurrentPrice() >= topic.getCurrentPrice()) {
                throw new RuntimeException("money not enough");
            }

            fixedRankingTopics.put(ranking, topic.merge(oldFixedRankingTopic));

            return;
        }

        Topic oldTopic = getTopic(topic);

        if (oldTopic == null) {
            throw new RuntimeException("no such topic");
        }

        fixedRankingTopics.put(ranking, topic.merge(oldTopic));

        // delete previous topic as README said.
        topics.remove(topic);
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
                    ? fixedRankingTopics.get(i)
                    : topicIterator.next();

            rankingList.add(topic);
        }

        return rankingList;
    }

    private Topic getTopic(Topic topic) {
        return topics.stream()
                .filter(t -> t.equals(topic))
                .findFirst()
                .orElse(null);
    }
}
