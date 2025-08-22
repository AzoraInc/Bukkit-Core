package com.zerosio.enums;

public enum Status {

    ONE_YEAR("§6", "1 Year Veteran", "§6✦", true),
    TWO_YEAR("§6", "2 Year Veteran", "§e✦", true),
    THREE_YEAR("§6", "3 Year Veteran", "§c✦", true),
    FOUR_YEAR("§6", "4 Year Veteran", "§b✦", true),
    FIVE_YEAR("§6", "5 Year Veteran", "§d✦", true),
    SIX_YEAR("§6", "6 Year Veteran", "§a✦", true),
    SEVEN_YEAR("§6", "7 Year Veteran", "§9✦", true),
    EIGHT_YEAR("§6", "8 Year Veteran", "§3✦", true),
    NINE_YEAR("§6", "9 Year Veteran", "§5✦", true),
    TEN_YEAR("§6", "10 Year Veteran", "§4✦", true),

    AFK("§7", "AFK", "§8✱", true),
    GRINDING_WINS("§a", "Grinding Wins", "§a⚔", true),
    GRINDING_KILLS("§c", "Grinding Kills", "§c☠", true),
    LOOKING_FOR_GUILD("§9", "Looking for a Guild", "§b✉", true),
    DO_NOT_DISTURB("§4", "Do Not Disturb", "§4✖", true),
    JUST_CHILLING("§e", "Just Chillin'", "§e❄", true),
    LOOKING_TO_CHAT("§d", "Looking to Chat", "§d✦", true),
    LOOKING_TO_TEAM("§b", "Looking to Team Up", "§b✚", true),
    BOOP("§6", "Boop!", "§6☀", true),

    SPECIAL_STATUS("§d", "Hypixel Wiki Team", "§d✎", true),
    KARAOKE_STAFF("§3", "Karaoke Staff", "§3♫", true),
    PLAYER_COUNCIL("§c", "Player Council", "§c⚖", true),

    NONE("", "", "", false);

    private final String color;
    private final String name;
    private final String symbol;
    private final boolean hasSymbol;

    Status(String color, String name, String symbol, boolean hasSymbol) {
        this.color = color;
        this.name = name;
        this.symbol = symbol;
        this.hasSymbol = hasSymbol;
    }

    public String getPrefix() {
        return hasSymbol ? symbol + " " + color + name : color + name;
    }
}
