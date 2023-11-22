package com.lunardev.oneblock.settings;

import org.mineacademy.fo.settings.SimpleLocalization;

public class Localization extends SimpleLocalization {

    public static class Blocks {

        public static String BLOCK_CREATED = "&aA new block has been created just for you!";
        public static final String BLOCK_DELETED = "&aYour block has been deleted!`";
        public static String BLOCK_ALREADY_EXISTS = "&cYou already have a block!";
        public static String BLOCK_DOES_NOT_EXIST = "&cYou do not have a block!, create one with '&e/ob create&c'!";
        public static final String BLOCK_NOT_FOUND = "&cCould not find block!";
        public static String TELEPORT = "&aYou have been teleported to your block.";
        public static String TELEPORT_OTHER = "&aYou have been teleported to the block of &e{player}&a!"; //  TODO Implement this feature
        public static String TELEPORT_OTHER_TO_YOU = "&a{player} has been teleported to your block."; //  TODO Implement this feature
        public static String TELEPORT_OTHER_TO_OTHER = "&a{player} has been teleported to {player2}'s block."; // TODO Implement this feature

        public static String LEVEL_UP = "&aYou leveled up to level &e{level}&a!";
        public static String LEVEL_UP_BROADCAST = "&e{0} &aleveled up to level &e{1}&a!";
        public static String LEVEL_UP_TITLE = "&aLevel Up!";
        public static String LEVEL_UP_SUBTITLE = "&eYou leveled up to level &a{0}&e!";

        public static String[] INFO_COMMAND_MESSAGE = {
                "&aIsland details of &e{player}&a:",
                "&aYour current level is &e{level}&a!",
                "&aYou have mined &e{blocks} &ablocks!",
                "&aYour current xp is &e{xp}&a!",
                "&aYou need &e{nextXp} &axp to level up!"
        };
    }

    public static class Commands {

        public static String PLAYER_NOT_FOUND = "&cCould not find player!";
        public static String MUST_ENTER_PLAYER_NAME = "&cYou must enter a player name!";
        public static String CANNOT_CHECK_ALL_BLOCKS = "&cYou cannot check island details of all players.";

    }

}
