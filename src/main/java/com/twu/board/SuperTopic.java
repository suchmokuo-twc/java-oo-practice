package com.twu.board;

public class SuperTopic extends Topic {
    public SuperTopic(String description) {
        super(description);
    }

    @Override
    protected void increasePopularity(int points) {
        super.increasePopularity(points * 2);
    }
}
