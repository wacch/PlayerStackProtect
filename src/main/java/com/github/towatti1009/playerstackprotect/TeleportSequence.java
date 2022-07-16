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
	private final float[] exeLoc;
	private float count;
	private static Set<Player> addableState = new HashSet<Player>();
	
	public TeleportSequence(Player _pl, int _count) {
		pl = _pl;
		count = _count * 20;
		exeLoc = new float[]{(float)Math.round(pl.getLocation().getX()*10)/10, (float)Math.round(pl.getLocation().getY()*10)/10, (float)Math.round(pl.getLocation().getZ()*10)/10};
		addableState.add(pl);
	}

	@Override
	public void run() {
		//テレポート実行
		if(count == 0) {
			cancel();
			pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""));
			pl.teleport(PlayerPosMap.getLatestEntries(pl.getName()));
			PlayerPosMap.removeLatestEntries(pl.getName());
			addableState.remove(pl);
			pl.sendMessage(ChatColor.LIGHT_PURPLE.toString() + "テレポートしました。脱出できなかった場合には、再度\"/stack\"コマンドを実行してください。");
			return;
		}
		
		//ここらへんの座標比較があまり美しくない
		//移動時キャンセル処理
		float[] comLoc = new float[]{(float)Math.round(pl.getLocation().getX()*10)/10, (float)Math.round(pl.getLocation().getY()*10)/10, (float)Math.round(pl.getLocation().getZ()*10)/10};
		if(exeLoc[0] != comLoc[0] || exeLoc[1] != comLoc[1] || exeLoc[2] != comLoc[2]) {
			cancel();
			pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED.toString() + "テレポートはキャンセルされました"));
			addableState.remove(pl);
			return;
		}
		
		//秒数更新
		pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GOLD.toString() + count/20 + "秒後にテレポートします"));
		count -= 2;
	}
	
	public static boolean getAddableState(Player pl) {
		return addableState.contains(pl);
	}
}