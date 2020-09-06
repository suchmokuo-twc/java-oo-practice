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

        Topic topic = board.getTopic(new Topic(description));
        topic.increasePopularity(points);
        board.updateTopic(topic);
        this.points -= points;
    }

    public void buyTopicRanking(Board board, String description, int ranking, int bid) {
        Topic topic = new Topic(description, bid);

        board.setFixedTopicRanking(topic, ranking);
    }
}
