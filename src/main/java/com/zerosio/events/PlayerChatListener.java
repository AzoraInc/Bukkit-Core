package com.zerosio.events;

import com.zerosio.enums.Rank;
import com.zerosio.CoreAPI;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {
	@EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Rank rank = CoreAPI.getPlayerRank(player.getUniqueId());
        String prefix = rank.getPrefix(); 
        
        String format = null;
        if (rank.isAboveOrEqual(Rank.YOUTUBE)) {	
        format = prefix + player.getName() + "§f: " + event.getMessage().replace("<3", "§c❤").replace("⭐", "§6✭").replace(":owo:", "§dO§5w§dO").replace("o/", "§d(/◕ヮ◕)/").replace(":OOF:", "§c§lOOF").replace(":123:", "§a1§e2§c3").replace(":shrug:", "§e¯\\(ツ)/¯").replace(":yes:", "§a✔").replace(":no:", "§c✖").replace(":java:", "§b♨").replace(":arrow:", "§e➡").replace(":typing:", "§e✎§6...");
        } else {
        	format = prefix + player.getName() + ": " + event.getMessage();
        }
        event.setFormat(format);
    }
}