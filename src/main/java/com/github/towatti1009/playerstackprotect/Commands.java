package com.github.towatti1009.playerstackprotect;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor{
	//TAB補完機能付けたかったけど1mmも意味わからん
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("stack")) {
			if(Config.getBooleanConfig("enable") && Config.canConfigLoad()) {
				if (sender instanceof Player) {
					sender.sendMessage(ChatColor.LIGHT_PURPLE.toString() + Config.getIntegerConfig("TimeToTeleport") + "秒後にテレポートします。何か入力をするとキャンセルされます");
					//テレポートまでの時間をここで設定
					new TeleportSequence((Player)sender,Config.getIntegerConfig("TimeToTeleport")).runTaskTimer(Main.getPlugin(),0L,2L);
					return true;
				}
			} else {
			sender.sendMessage(ChatColor.RED.toString() + "プラグインは無効化されています");
			}
		}
		
		if (command.getName().equalsIgnoreCase("psp")) {
			String subCmd = args.length == 0 ? "" : args[0];
			if(subCmd.equalsIgnoreCase("enable") || subCmd.equalsIgnoreCase("debug")) {
				if(Config.setBooleanConfig(args[0], args[1])) {
					sender.sendMessage(ChatColor.GRAY + "項目 \"" + args[0] + "\" を" + args[1] + "に変更しました");
					sender.sendMessage(ChatColor.GRAY + "設定を反映するには、\"/psp reload\"を実行してください");
				} else {
					sender.sendMessage(ChatColor.RED + "項目 \"" + args[0] + "\" はBoolean型である必要があります: " + ChatColor.UNDERLINE + args[1]);
					return false;
				}
			}
			if(subCmd.equalsIgnoreCase("reload")) {
				Config.configLoad();
				sender.sendMessage(ChatColor.GRAY + "Configを再読み込みしました");
			}
		}
		return true;
    }
}
