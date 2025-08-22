package com.zerosio;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

import com.zerosio.database.User;
import com.zerosio.enums.Rank;
import com.zerosio.enums.Status;

public class CoreAPI {

    public static Rank getPlayerRank(UUID uuid) {
        User user = User.getUser(uuid);
        return user != null ? user.getRank() : Rank.DEFAULT;
    }

    public static Rank getPlayerRank(String uuid) {
        return getPlayerRank(UUID.fromString(uuid));
    }
    
    public static void setRank(Player player, Rank rank) {
    	User user = User.getUser(player.getUniqueId());
    	
    	if (rank.isBelowOrEqual(Rank.YOUTUBE)) {
    		Rank oldPkgRank = getPlayerRank(player.getUniqueId());
    		
    		user.setData("package_rank", oldPkgRank.name().toUpperCase());
    		user.setData("new_package_rank", rank.name().toUpperCase());		
    		return;
    	}
    	
    	user.setData("rank", rank.name().toUpperCase());
    }
    
    public static void setMonthlyRank(Player player, Rank rank) {
    	User.getUser(player.getUniqueId()).setMonthlyRank(rank);
    }
    
    public static void setStatus(Player player, Status status) {
    	User user = User.getUser(player.getUniqueId());
    	
    	user.setData("active_status", status.name().toUpperCase());
    }
    
    public static Status getStatus(UUID uuid) {
        User user = User.getUser(uuid);
        if (user == null) return null;
        String statusString = user.getData("active_status");
        return statusString != null ? Status.valueOf(statusString) : null;
    }

    public static Status getStatus(String uuid) {
        return getStatus(UUID.fromString(uuid));
    }

    public static boolean isInAdminDebug(UUID uuid) {
        User user = User.getUser(uuid);
        return user != null && user.getBoolean("debug_mode");
    }

    public static boolean isInAdminDebug(String uuid) {
        return isInAdminDebug(UUID.fromString(uuid));
    }

    public static void debug(UUID uuid, String message) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            User user = User.getUser(uuid);
            if (user != null) user.debug(player, message);
        }
    }

    public static void debug(String uuid, String message) {
        debug(UUID.fromString(uuid), message);
    }

    public static Player getPlayer(String name) {
        return Bukkit.getPlayer(name);
    }

    public static Player getPlayerUsingUUID(UUID uuid) {
        return Bukkit.getPlayer(uuid);
    }
}
