package com.github.towatti1009.playerstackprotect;

import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener{	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if(Config.getBooleanConfig("enable") && Config.canConfigLoad()) {
			Player pl = e.getPlayer();
			PlayerPosMap.initEntries(pl.getName(), pl.getLocation());
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		PlayerPosMap.deleteKeys(e.getPlayer().getName());
	}
}
