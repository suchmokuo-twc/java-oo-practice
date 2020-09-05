package com.twu.user;

import com.twu.board.Board;
import com.twu.board.SuperTopic;

public class Admin extends User {
    public Admin(String name) {
        super(name);
    }

    public void addSuperTopic(Board board, String description) {
        board.addTopic(new SuperTopic(description));
    }
}
