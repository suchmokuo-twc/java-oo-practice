package com.twu.board;

public class Topic implements Comparable<Topic> {
    protected final String description;
    protected int popularity;
    protected int currentPrice;

    public Topic(String description) {
        this.description = description;
        this.popularity = 0;
        this.currentPrice = 0;
    }

    public Topic(String description, int currentPrice) {
        this.description = description;
        this.popularity = 0;
        this.currentPrice = currentPrice;
    }

    public String getDescription() {
        return description;
    }

    public int getPopularity() {
        return this.popularity;
    }

    public Topic merge(Topic topic) {
        if (this.popularity == 0) {
            this.popularity = topic.popularity;
        }

        if (this.currentPrice == 0) {
            this.currentPrice = topic.currentPrice;
        }

        return this;
    }

    protected void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    protected int getCurrentPrice() {
        return currentPrice;
    }

    protected void increasePopularity(int points) {
        this.popularity += points;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof Topic) {
            Topic topic = (Topic) obj;
            return description.equalsIgnoreCase(topic.description);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return description.toLowerCase().hashCode();
    }

    @Override
    public int compareTo(Topic topic) {
        return topic.popularity - this.popularity;
    }
}
