package com.twu.board;

public class Topic implements Comparable<Topic> {
    protected final String description;
    protected int popularity;

    public Topic(String description) {
        this.description = description;
        this.popularity = 0;
    }

    public String getDescription() {
        return description;
    }

    public int getPopularity() {
        return this.popularity;
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
