package me.intronate67.TotalWarfare.commands;

import me.intronate67.TotalWarfare.SettingsManager;

import org.bukkit.entity.Player;



public class SetStatsWall implements SubCommand {

    @Override
    public boolean onCommand(Player player, String[] args) {
        //StatsWallManager.getInstance().setStatsSignsFromSelection(player);
        return false;
    }

    
    public String help(Player p){
        return "/tw setstatswall - "+ SettingsManager.getInstance().getMessageConfig().getString("messages.help.setstatswall", "Sets the stats wall");
    }

	@Override
	public String permission() {
		return null;
	}
}