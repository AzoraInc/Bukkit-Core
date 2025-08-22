package com.zerosio.enums;

public enum Rank {

    DEFAULT("§7", ""),
    YOUTUBE("§c", "§fYOUTUBE"),
    BETA_TESTER("§d", "BT"),
    STAFF("§9", "STAFF"),
    MOD("§2", "MOD"),
    ADMIN("§c", "ADMIN"),
    DEV("§3", "DEV"),
    OWNER("§c", "OWNER");

    private final String prefix;
    private final String color;

    Rank(String color, String prefix) {
        this.color = color;
        this.prefix = prefix;
    }

    public static Rank getRankOrDefault(int level) {
        for (Rank rank : Rank.values()) {
            if (rank.getLevel() == level) {
                return rank;
            }
        }
        return DEFAULT;
    }

    public int getLevel() {
        return this.ordinal() + 1; 
    }

    public String getScoreRank() {
        return this == DEFAULT ? "§7Default" : getPrefixColoured();
    }

    public String getPrefix() {
        return this == DEFAULT ? color : color + "[" + prefix + color + "] ";
    }

    public String getColour() {
        return color;
    }

    public String getPrefixx() {
        return prefix;
    }

    public String getPrefixColoured() {
        return color + prefix;
    }

    public boolean isBelowOrEqual(Rank rank) {
        return this.getLevel() <= rank.getLevel();
    }

    public boolean isAboveOrEqual(Rank rank) {
        return this.getLevel() >= rank.getLevel();
    }

    public boolean hasRank(Rank requiredRank) {
        return this.getLevel() >= requiredRank.getLevel();
    }

    public boolean isStaff() {
        return this.ordinal() >= STAFF.ordinal();
    }

    public boolean isDefaultPermission() {
        return this == DEFAULT;
    }

    public String getFormattedRank() {
        return prefix;
    }
}
