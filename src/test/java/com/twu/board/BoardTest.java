package com.twu.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

    @BeforeEach
    void init() {
        board = new Board();

        board.addTopic(new Topic("a"));
        board.addTopic(new Topic("b"));
        board.addTopic(new Topic("c"));
    }

    @Test
    void should_set_fixed_ranking() {
        board.setFixedTopicRanking(new Topic("c"), 1, 0);

        List<Topic> expected = Arrays.asList(
                new Topic("c"),
                new Topic("a"),
                new Topic("b")
        );

        assertIterableEquals(board.getRankingList(), expected);
    }

    @Test
    void should_set_fixed_ranking_fail() {
        board.setFixedTopicRanking(new Topic("c"), 1, 100);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            board.setFixedTopicRanking(new Topic("a"), 1, 90);
        });

        String expectedMessage = "money not enough";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
