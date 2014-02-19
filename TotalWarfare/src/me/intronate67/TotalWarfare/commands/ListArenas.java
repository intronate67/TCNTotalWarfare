package me.intronate67.TotalWarfare.commands;

import me.intronate67.TotalWarfare.Game;
import me.intronate67.TotalWarfare.GameManager;
import me.intronate67.TotalWarfare.MessageManager;
import me.intronate67.TotalWarfare.SettingsManager;
import me.intronate67.TotalWarfare.MessageManager.PrefixType;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;



public class ListArenas implements SubCommand{

    public boolean onCommand(Player player, String[] args) {
    	StringBuilder arenas = new StringBuilder();
    	try{
    	if(args.length == 0 || Integer.parseInt(args[0]) < 0 || Integer.parseInt(args[0]) > GameManager.getInstance().getGameCount()){
    		MessageManager.getInstance().sendMessage(PrefixType.ERROR, "error.gamenoexist", player);
    	}
    	if (GameManager.getInstance().getGames().isEmpty()) {
    		arenas.append(SettingsManager.getInstance().getMessageConfig().getString("messages.words.noarenas", "No arenas")).append(": ");
    		player.sendMessage(ChatColor.RED + arenas.toString());
        	return true;
    	}
    	arenas.append(SettingsManager.getInstance().getMessageConfig().getString("messages.words.noarenas", "Arenas")).append(": ");
        for (Game g : GameManager.getInstance().getGames()) {
        	arenas.append(g.getID()).append(", ");
        }
        player.sendMessage(ChatColor.GREEN + arenas.toString());
    	}catch(Exception e){
    		MessageManager.getInstance().sendMessage(PrefixType.ERROR, "error.gamenoexist", player);
    	}
        return false;
    }
    
    @Override
    public String help(Player p) {
        return "/tw listarenas - " + SettingsManager.getInstance().getMessageConfig().getString("messages.help.listarenas", "List all available arenas");
    }

	@Override
	public String permission() {
		return "";
	}
}