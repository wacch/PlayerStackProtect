package com.github.towatti1009.playerstackprotect;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GetPlayerPosScheduler extends BukkitRunnable{
	@Override
	public void run() {
		boolean debug = Config.getBooleanConfig("debug");
		for(Player allPlayers : Bukkit.getOnlinePlayers()) {
			if(!TeleportSequence.getAddableState(allPlayers)) {
				if(debug)
					allPlayers.sendMessage("DEBUG:座標記録を試みます");
	       		PlayerPosMap.tryAddEntries(allPlayers.getName(), allPlayers.getLocation(),allPlayers);
			}else{
				if(debug)
					allPlayers.sendMessage("DEBUG:テレポートシークエンス中です");
			}
        }
	}
}
