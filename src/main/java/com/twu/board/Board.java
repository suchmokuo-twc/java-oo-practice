package com.twu.board;

import com.twu.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Board {
    private final Set<Topic> generalTopics = new HashSet<>(8);
    private final Map<Integer, Topic> fixedRankingTopics = new HashMap<>(8);

    public void addTopic(Topic topic) {
        generalTopics.add(topic);
    }

    public void setFixedTopicRanking(Topic topic, int ranking) {
        if (ranking < 1 || ranking > totalTopics()) {
            throw new RuntimeException("range error");
        }

        Topic topicInRanking = getTopic(topic);
        topic.merge(topicInRanking);
        Topic oldFixedRankingTopic = fixedRankingTopics.get(ranking);

        if (oldFixedRankingTopic != null && oldFixedRankingTopic.getCurrentPrice() >= topic.getCurrentPrice()) {
            throw new RuntimeException("money not enough");
        }

        if (generalTopics.contains(topic)) {
            fixedRankingTopics.put(ranking, topic);
            // delete previous topic as README said.
            generalTopics.remove(topic);
        } else {
            // topic is in the fixed ranking map.
            removeTopicInFixedRanking(topic);
            fixedRankingTopics.put(ranking, topic);
        }
    }

    public Topic getTopic(Topic topic) {
        List<Topic> rankingList = getRankingList();
        int index = rankingList.indexOf(topic);

        if (index == -1) {
            throw new RuntimeException("no such topic");
        }

        return rankingList.get(index);
    }

    public void updateTopic(Topic topic) {
        Topic topicInRanking = getTopic(topic);
        topic.merge(topicInRanking);

        if (generalTopics.contains(topic)) {
            generalTopics.add(topic);
        } else {
            int ranking = Utils.getKey(fixedRankingTopics, topic);
            fixedRankingTopics.put(ranking, topic);
        }
    }

    public List<Topic> getRankingList() {
        int len = totalTopics();
        List<Topic> rankingList = new ArrayList<>(len);

        Iterator<Topic> topicIterator = generalTopics.stream().sorted().iterator();

        for (int i = 1; i <= len; i++) {
            Topic topic = fixedRankingTopics.containsKey(i)
                    ? fixedRankingTopics.get(i)
                    : topicIterator.next();

            rankingList.add(topic);
        }

        return rankingList;
    }

    private int totalTopics() {
        return generalTopics.size() + fixedRankingTopics.size();
    }

    private void removeTopicInFixedRanking(Topic topic) {
        if (fixedRankingTopics.containsValue(topic)) {
            fixedRankingTopics.entrySet().removeIf(entry -> topic.equals(entry.getValue()));
        }
    }
}
