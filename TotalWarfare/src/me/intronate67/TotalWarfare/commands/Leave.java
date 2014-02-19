package me.intronate67.TotalWarfare.commands;

import me.intronate67.TotalWarfare.GameManager;
import me.intronate67.TotalWarfare.MessageManager;
import me.intronate67.TotalWarfare.SettingsManager;

import org.bukkit.entity.Player;



public class Leave implements SubCommand {

    public boolean onCommand(Player player, String[] args) {
        if (GameManager.getInstance().getPlayerGameId(player) == -1) {
            MessageManager.getInstance().sendFMessage(MessageManager.PrefixType.ERROR, "error.notinarena", player);
        }
        else{
            GameManager.getInstance().removePlayer(player, false);
        }
        return true;
    }

    @Override
    public String help(Player p) {
        return "/tw leave - " + SettingsManager.getInstance().getMessageConfig().getString("messages.help.leave", "Leaves the game");
    }

	@Override
	public String permission() {
		return null;
	}
}