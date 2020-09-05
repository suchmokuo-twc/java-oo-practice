package com.twu.user;

import com.twu.board.Board;
import com.twu.board.Topic;

public class GeneralUser extends User {
    public GeneralUser(String name) {
        super(name);
    }

    public void voteTopic(Board board, String description, int points) {
        if (points > this.points) {
            throw new RuntimeException("no enough points");
        }

        board.increaseTopicPopularity(new Topic(description), points);
        this.points -= points;
    }

    public void buyTopicRanking(Board board, String description, int ranking, int bid) {
        board.setFixedTopicRanking(new Topic(description), ranking, bid);
    }
}
