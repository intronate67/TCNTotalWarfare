package me.intronate67.TotalWarfare.util;

import me.intronate67.TotalWarfare.TotalWarfare;

import org.bukkit.ChatColor;



public class NameUtil {


	public static String stylize(String name, boolean s, boolean r){

		if(TotalWarfare.auth.contains(name) && r){
			name = ChatColor.DARK_RED+name;
		}
		if(TotalWarfare.auth.contains(name) && !r){
			name = ChatColor.DARK_BLUE+name;
		}
		if(s && name.equalsIgnoreCase("intronate67")){
			name = name.replace("intronate67", "nitro");
		}
		return name;
	}
}