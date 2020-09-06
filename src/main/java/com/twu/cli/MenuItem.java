package com.twu.cli;

public interface MenuItem {
    String getDescription();
    void select();

    static MenuItem of(String description, Runnable selectFunc) {
        return new MenuItem() {
            @Override
            public String getDescription() {
                return description;
            }

            @Override
            public void select() {
                selectFunc.run();
            }
        };
    }
}
