package me.intronate67.TotalWarfare.commands;

import me.intronate67.TotalWarfare.MessageManager;
import me.intronate67.TotalWarfare.MessageManager.PrefixType;
import me.intronate67.TotalWarfare.SettingsManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;

public class SetCapture implements SubCommand{
	WorldEditPlugin getWorldEdit() {
		Plugin worldEdit = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
		if (worldEdit instanceof WorldEditPlugin) {
			return (WorldEditPlugin) worldEdit;
		} else {
			return null;
		}
	}
	public boolean onCommand(Player pl, String[] args){
		FileConfiguration c = SettingsManager.getInstance().getSpawns();
		//SettingsManager s = SettingsManager.getInstance();
		String no;
		MessageManager msgmgr = MessageManager.getInstance();
		WorldEditPlugin we = getWorldEdit();
		Selection sel = we.getSelection(pl);
		if (sel == null) {
			msgmgr.sendMessage(PrefixType.WARNING, "You must make a WorldEdit Selection first!", pl);
			return true;
		}
		Location max = sel.getMaximumPoint();
		Location min = sel.getMinimumPoint();
		no = args[0];
		
		
		SettingsManager.getInstance().saveSpawns();
		pl.sendMessage(ChatColor.GREEN + "Capture Location for Arena ID: " + no + " Succesfully added");
		if(args.length == 1){
			if(!pl.hasPermission(permission()) && !pl.isOp()){
				MessageManager.getInstance().sendFMessage(MessageManager.PrefixType.ERROR, "error.nopermission", pl);
	            return true;
			}
			SettingsManager.getInstance().getSpawns().set(("spawns." + no), null);
			c.set("spawns." + no + ".capture" + ".world", max.getWorld().getName());
			c.set("spawns." + no + ".capture" + ".x1", max.getBlockX());
			c.set("spawns." + no + ".capture" +  ".y1", max.getBlockY());
			c.set("spawns." + no + ".capture" +  ".z1", max.getBlockZ());
			c.set("spawns." + no + ".capture" +  ".x2", min.getBlockX());
			c.set("spawns." + no + ".capture" +  ".y2", min.getBlockY());
			c.set("spawns." + no + ".capture" +  ".z2", min.getBlockZ());
		}
		return false;
	}
	
	
	@Override
	public String help(Player p) {
		return "/tw setCapture - " + SettingsManager.getInstance().getMessageConfig().getString("messages.help.join", "Set a capture location.");
	}

	@Override
	public String permission() {
		return "tw.arena.setcapture";
	}

}
