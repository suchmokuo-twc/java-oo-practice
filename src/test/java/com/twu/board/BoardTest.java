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
        board.setFixedTopicRanking(new Topic("c"), 1);

        List<Topic> expected = Arrays.asList(
                new Topic("c"),
                new Topic("a"),
                new Topic("b")
        );

        assertIterableEquals(board.getRankingList(), expected);
    }

    @Test
    void should_set_fixed_ranking_fail_if_no_enough_money() {
        board.setFixedTopicRanking(new Topic("c", 100), 1);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            board.setFixedTopicRanking(new Topic("a", 90), 1);
        });

        String expectedMessage = "money not enough";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void should_set_fixed_ranking_fail_if_no_such_topic() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            board.setFixedTopicRanking(new Topic("d"), 1);
        });

        String expectedMessage = "no such topic";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        board.setFixedTopicRanking(new Topic("a"), 1);

        exception = assertThrows(RuntimeException.class, () -> {
            board.setFixedTopicRanking(new Topic("d"), 1);
        });

        actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void should_set_fixed_ranking_for_topic_already_in_fixed_ranking_list() {
        board.setFixedTopicRanking(new Topic("a"), 1);
        board.setFixedTopicRanking(new Topic("b"), 2);
        board.setFixedTopicRanking(new Topic("b", 10), 1);

        assertIterableEquals(board.getRankingList(), Arrays.asList(
                new Topic("b"),
                new Topic("c")
        ));
    }
}
