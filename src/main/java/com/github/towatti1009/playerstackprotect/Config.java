package com.github.towatti1009.playerstackprotect;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Config extends JavaPlugin{
	private static FileConfiguration config = null;
	private static Map<String, Boolean> bnData = new HashMap<>();
	private static Map<String, String> sgData = new HashMap<>();
	private static Map<String, Integer> itData = new HashMap<>();
	private static boolean underReload = true;
	
	public static boolean configLoad() {
		final int configVersion = 1;
		underReload = false;
		String[][] bn = new String[][] {{"enable","true"},{"debug","false"}};
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
    	
    	if(config.getInt("Config-version") < configVersion) {
    		config.options().copyDefaults(true);
    		config.set("Config-version", configVersion);
    		plg.saveConfig();
    	}
    	
    	String regexp = "^(0$|[1-9]\\d*)";
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
        			underReload = true;
        			return false;
        		}
        	}
    	} catch(Exception e) {
    		plg.getLogger().info(ChatColor.RED + "valueOf変換エラー" + e.toString());
    		underReload = true;
    		return false;
    	}
    	underReload = true;
    	return true;
    }
	
	public static boolean getBooleanConfig(String key) {
		return bnData.get(key);
	}
	
	public static boolean setBooleanConfig(String key,String value) {
		if(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
			config.set(key,Boolean.valueOf(value));
			Main.getPlugin().saveConfig();
		} else {
			return false;
		}
		return true;
	}
	
	public static String getStringConfig(String key) {
		return sgData.get(key);
	}

	public static int getIntegerConfig(String key) {
		return itData.get(key);
	}
	
	public static boolean canConfigLoad() {
		return underReload;
	}
}
