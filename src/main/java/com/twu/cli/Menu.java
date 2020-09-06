package com.twu.cli;

import com.twu.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.twu.Main.textIO;
import static com.twu.Main.terminal;

public class Menu implements MenuItem {

    private final List<MenuItem> items = new ArrayList<>();
    private final String description;
    private final Menu parentMenu;
    private final MenuItem goBack;

    public Menu(Menu parentMenu, String description) {
        this.description = description;
        this.parentMenu = parentMenu;
        this.goBack = new GoBack(parentMenu);
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public void show() {
        printMenu();
        MenuItem selectedItem = getSelectedItem();

        try {
            selectedItem.select();
        } catch (RuntimeException exception) {
            terminal.println("操作失败: " + exception.getMessage());
        }

        show();
    }

    @Override
    public void select() {
        show();
    }

    @Override
    public String getDescription() {
        return description;
    }

    private void printMenu() {
        Stream<MenuItem> itemStream =
                parentMenu == null
                        ? items.stream()
                        : Stream.concat(items.stream(), Stream.of(goBack));

        terminal.println("=================================================================================");
        terminal.println(description + "：");

        Utils.streamForEach(itemStream, (item, i) -> terminal.printf("%d. %s\n", i + 1, item.getDescription()));
    }

    private MenuItem getSelectedItem() {
        int itemsCount = parentMenu == null ? items.size() : items.size() + 1;

        int selectedItemIndex = textIO.newIntInputReader()
                .withMinVal(1)
                .withMaxVal(itemsCount)
                .read("请选择");

        return selectedItemIndex <= items.size() ? items.get(selectedItemIndex - 1) : goBack;
    }

    public static Menu of(Menu parentMenu, String description, MenuItem... items) {
        Menu menu = new Menu(parentMenu, description);

        for (MenuItem item : items) {
            menu.addItem(item);
        }

        return menu;
    }

    public static Menu ofEntryMenu(String description, MenuItem... items) {
        return of(null, description, items);
    }

    private static class GoBack implements MenuItem {

        private final Menu targetMenu;

        public GoBack(Menu targetMenu) {
            this.targetMenu = targetMenu;
        }

        @Override
        public void select() {
            targetMenu.show();
        }

        @Override
        public String getDescription() {
            return "返回";
        }
    }
}
