package com.zerosio;

import com.zerosio.database.User;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;

    @Override
    public void onEnable() {
     instance = this;
     User.connect();
    }

    @Override
    public void onDisable() {
    	
    }

    public static Main getInstance() {
        return instance;
    }
}