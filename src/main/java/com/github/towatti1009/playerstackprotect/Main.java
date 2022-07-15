package com.github.towatti1009.playerstackprotect;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	private static Plugin plg;

    @Override
    public void onEnable() {
    	plg = this;
    	if(Config.configLoad()) {
    		Bukkit.getServer().getPluginManager().registerEvents(new EventListener(), this);
    		getCommand("stack").setExecutor(new Commands());
    		//getCommand("PlayerStackProtect").setExecutor(new Commands());
    		//座標を記録する間隔をここで設定
    		new GetPlayerPosScheduler().runTaskTimer(Main.getPlugin(),Config.getIntegerConfig("TimeToFirstRecord"),Config.getIntegerConfig("SaveLocationsInterval"));
    		getLogger().info(ChatColor.GOLD + "PlayerStackProtect Launched! v1.0α");
    	} else {
    		getLogger().info(ChatColor.RED + "Launch failed, please check the format of config.");
    	}
    }
    
    public static Plugin getPlugin() {
        return plg;
    }
}
