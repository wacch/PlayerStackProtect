package com.github.towatti1009.playerstackprotect;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor{
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("stack")) {
			if(Config.getBooleanConfig("Enable")) {
				if (sender instanceof Player) {
					sender.sendMessage("[PlayerStackProtect]" + Config.getIntegerConfig("TimeToTeleport") + "秒後にテレポートします。何か入力をするとキャンセルされます。");
					//テレポートまでの時間をここで設定
					new TeleportSequence((Player)sender,Config.getIntegerConfig("TimeToTeleport")).runTaskTimer(Main.getPlugin(),0L,2L);
					return true;
				}
			}
		}
		if (command.getName().equalsIgnoreCase("PlayerStackProtect")) {
			sender.sendMessage("[PlayerStackProtect]" + "管理者用コマンドです");
			return true;
		}
		return false;
    }
}
