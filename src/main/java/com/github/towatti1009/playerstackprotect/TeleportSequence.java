package com.github.towatti1009.playerstackprotect;

import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TeleportSequence extends BukkitRunnable{
	private final Player pl;
	private final int[] exeLoc;
	private float count;
	private static Set<Player> addableState = new HashSet<Player>();
	
	public TeleportSequence(Player _pl, int _count) {
		pl = _pl;
		count = _count * 20;
		exeLoc = new int[]{(int)Math.round(pl.getLocation().getX()), (int)Math.round(pl.getLocation().getY()), (int)Math.round(pl.getLocation().getZ())};
		addableState.add(pl);
	}

	@Override
	public void run() {
		//テレポート実行
		if(Config.getBooleanConfig("Enable")) {
			if(count == 0) {
				cancel();
				pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""));
				pl.teleport(PlayerPosMap.getLatestEntries(pl.getName()));
				PlayerPosMap.removeLatestEntries(pl.getName());
				addableState.remove(pl);
				return;
			}
			
			//ここらへんの座標比較があまり美しくない
			//移動時キャンセル処理
			int[] comLoc = new int[]{(int)Math.round(pl.getLocation().getX()), (int)Math.round(pl.getLocation().getY()), (int)Math.round(pl.getLocation().getZ())};
			if(exeLoc[0] != comLoc[0] || exeLoc[1] != comLoc[1] || exeLoc[2] != comLoc[2]) {
				cancel();
				pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED.toString() + "テレポートはキャンセルされました"));
				addableState.remove(pl);
				return;
			}
			
			//秒数更新
			pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GOLD.toString() + count/20 + "秒後にテレポートします"));
			count -= 2;
		} else {
			cancel();
			pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED.toString() + "プラグインは無効化されています"));
			addableState.remove(pl);
		}
	}
	
	public static boolean getAddableState(Player pl) {
		return addableState.contains(pl);
	}
}