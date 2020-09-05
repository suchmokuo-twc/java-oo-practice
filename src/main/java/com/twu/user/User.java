package com.twu.user;

import com.twu.board.Board;
import com.twu.board.Topic;

import java.util.List;
import java.util.stream.IntStream;

public class User {
    protected final String name;
    protected int points = 10;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public void browseRankingBoard(Board board) {
        List<Topic> rankingList = board.getRankingList();

        IntStream.range(0, rankingList.size()).forEach(index -> {
            Topic topic = rankingList.get(index);
            System.out.printf("%d. %s %d\n", index + 1, topic.getDescription(), topic.getPopularity());
        });
    }

    public void addTopic(Board board, String description) {
        board.addTopic(new Topic(description));
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof User) {
            User user = (User) obj;
            return name.equals(user.name);
        }

        return false;
    }
}
