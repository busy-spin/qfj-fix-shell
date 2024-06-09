package io.github.busy_spin.qfj_fix_shell.commands;

import org.springframework.shell.command.annotation.Command;

@Command(command = "color", group = "Color Demo")
public class ColorTestCommandHandler {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_GRAY = "\u001B[37m";
    public static final String ANSI_WHITE = "\u001B[37;1m";

    @Command(command = "just")
    public void print() {
        System.out.println(ANSI_BLUE + "red text" + ANSI_RESET);
    }

}