package com.twu.board;

public class SuperTopic extends Topic {
    public SuperTopic(String description) {
        super(description);
    }

    @Override
    public void increasePopularity(int points) {
        super.increasePopularity(points * 2);
    }
}
