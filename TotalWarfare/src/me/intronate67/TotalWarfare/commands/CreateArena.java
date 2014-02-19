package me.intronate67.TotalWarfare.commands;

import me.intronate67.TotalWarfare.GameManager;
import me.intronate67.TotalWarfare.MessageManager;
import me.intronate67.TotalWarfare.SettingsManager;

import org.bukkit.entity.Player;

public class CreateArena implements SubCommand{

    public boolean onCommand(Player player, String[] args) {
        if(!player.hasPermission(permission()) && !player.isOp()){
            MessageManager.getInstance().sendFMessage(MessageManager.PrefixType.ERROR, "error.nopermission", player);
            return true;
        }
        GameManager.getInstance().createArenaFromSelection(player);
        return true;
    }

    @Override
    public String help(Player p) {
        return "/tw createarena - " + SettingsManager.getInstance().getMessageConfig().getString("messages.help.createarena", "Create a new arena with the current WorldEdit selection");
    }

	@Override
	public String permission() {
		return "tw.admin.createarena";
	}
}
