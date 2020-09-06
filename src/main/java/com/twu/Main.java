package com.twu;

import com.twu.board.Board;
import com.twu.cli.Menu;
import com.twu.cli.MenuItem;
import com.twu.user.Admin;
import com.twu.user.GeneralUser;
import com.twu.user.User;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.util.HashSet;
import java.util.Set;

public class Main {

    private final Set<User> users = new HashSet<>(8);
    private Menu userMenu;
    private Menu adminMenu;
    private final Menu userTypeMenu;
    private User currentUser;
    private final Board board = new Board();

    private Main() {
        this.userTypeMenu = Menu.ofEntryMenu("选择用户类型",

                MenuItem.of("普通用户", () -> {
                    String name = textIO.newStringInputReader().read("用户名");
                    User newUser = new GeneralUser(name);
                    currentUser = users.stream()
                            .filter(u -> u.equals(newUser))
                            .findFirst()
                            .orElse(newUser);

                    if (currentUser == newUser) {
                        users.add(newUser);
                    }

                    userMenu.show();
                }),

                MenuItem.of("管理员", () -> {
                    currentUser = new Admin("admin");
                    users.add(currentUser);
                    adminMenu.show();
                })
        );

        this.userMenu = Menu.of(userTypeMenu, "用户菜单",

                MenuItem.of("查看热搜排行榜", () -> currentUser.browseRankingBoard(board)),

                MenuItem.of("给热搜事件投票", () -> {
                    GeneralUser user = (GeneralUser) currentUser;

                    terminal.printf("当前剩余票数：%d\n", user.getPoints());

                    String topic = textIO.newStringInputReader().read("选择话题");
                    int points = textIO.newIntInputReader().read("投票数");

                    user.voteTopic(board, topic, points);
                }),

                MenuItem.of("购买热搜", () -> {
                    GeneralUser user = (GeneralUser) currentUser;

                    String topic = textIO.newStringInputReader().read("选择话题");
                    int ranking = textIO.newIntInputReader().read("需要购买的排名");
                    int bid = textIO.newIntInputReader().withMinVal(0).read("出价");

                    user.buyTopicRanking(board, topic, ranking, bid);
                }),

                MenuItem.of("添加热搜", () -> {
                    String topic = textIO.newStringInputReader().read("输入话题");

                    currentUser.addTopic(board, topic);
                })
        );

        this.adminMenu = Menu.of(userTypeMenu, "管理员菜单",

                MenuItem.of("查看热搜排行榜", () -> currentUser.browseRankingBoard(board)),

                MenuItem.of("添加热搜", () -> {
                    String topic = textIO.newStringInputReader().read("输入话题");

                    currentUser.addTopic(board, topic);
                }),

                MenuItem.of("添加超级热搜", () -> {
                    Admin admin = (Admin) currentUser;

                    String topic = textIO.newStringInputReader().read("输入话题");

                    admin.addSuperTopic(board, topic);
                })
        );
    }

    public void run() {
        userTypeMenu.show();
    }

    public static final TextIO textIO = TextIoFactory.getTextIO();
    public static final TextTerminal<?> terminal = textIO.getTextTerminal();

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
}
