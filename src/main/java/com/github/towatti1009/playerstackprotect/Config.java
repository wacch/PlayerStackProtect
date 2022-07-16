package com.github.towatti1009.playerstackprotect;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Config extends JavaPlugin{
	public static FileConfiguration config = null;
	private static Map<String, Boolean> bnData = new HashMap<>();
	private static Map<String, String> sgData = new HashMap<>();
	private static Map<String, Integer> itData = new HashMap<>();
	
	public static boolean configLoad() {
		String[][] bn = new String[][] {{"Enable","true"},{"Debug","false"}};
		String[][] sg = new String[][] {{"LocationStoredWorld","world"}};
		String[][] it = new String[][] {{"SaveLocationsInterval","600"},{"TimeToFirstRecord","0"},{"TimeToTeleport","10"},{"LocationSaveCount","3"},{"DistanceThreshold","20"}};
		Plugin plg = Main.getPlugin();
		
    	plg.saveDefaultConfig();
    	if(config != null) {
    		plg.reloadConfig();
    		bnData.clear();
    		sgData.clear();
    		itData.clear();
    	}
    	config = plg.getConfig();
    	
    	String regexp = "^(0$|[1-9]\\d*)";
    	Pattern p = Pattern.compile(regexp);
    	try {
    		for(int i=0; i<bn.length; i++)
        		bnData.put(bn[i][0], config.getBoolean(bn[i][0],Boolean.valueOf(bn[i][1])));
        	for(int i=0; i<sg.length; i++)
        		sgData.put(sg[i][0], config.getString(sg[i][0],sg[i][1]));
        	for(int i=0; i<it.length; i++) {
        		//Matcher mi = p.matcher(it[i][1]);
        		if(it[i][1].matches(regexp)) {
        			itData.put(it[i][0], config.getInt(it[i][0],Integer.valueOf(it[i][1])));
        		}else{
        			plg.getLogger().info(ChatColor.RED + "正規表現パターンに一致していないものがあります:"+it[i][1]);
        			return false;
        		}
        	}
    	} catch(Exception e) {
    		plg.getLogger().info(ChatColor.RED + "valueOf変換エラー" + e.toString());
    		return false;
    	}
    	return true;
    }
	
	public static boolean getBooleanConfig(String s) {
		return bnData.get(s);
	}
	
	public static String getStringConfig(String s) {
		return sgData.get(s);
	}

	public static int getIntegerConfig(String s) {
		return itData.get(s);
	}
}
