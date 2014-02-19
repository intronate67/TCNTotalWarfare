package me.intronate67.TotalWarfare.commands;

import me.intronate67.TotalWarfare.MessageManager;
import me.intronate67.TotalWarfare.SettingsManager;

import org.bukkit.entity.Player;



public class SetLobbySpawn implements SubCommand {

    public boolean onCommand(Player player, String[] args) {
        if (!player.hasPermission(permission()) && !player.isOp()) {
            MessageManager.getInstance().sendFMessage(MessageManager.PrefixType.ERROR, "error.nopermission", player);
            return true;
        }
        SettingsManager.getInstance().setLobbySpawn(player.getLocation());
        MessageManager.getInstance().sendMessage(MessageManager.PrefixType.INFO, "info.lobbyspawn", player);
        return true;
    }
    
    @Override
    public String help(Player p) {
        return "/tw setlobbyspawn - " + SettingsManager.getInstance().getMessageConfig().getString("messages.help.setlobbyspawn", "Set the lobby spawnpoint");
    }

	@Override
	public String permission() {
		return "tw.admin.setlobby";
	}
}