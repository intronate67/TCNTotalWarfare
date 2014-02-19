package me.intronate67.TotalWarfare.commands;

import me.intronate67.TotalWarfare.GameManager;
import me.intronate67.TotalWarfare.MessageManager;
import me.intronate67.TotalWarfare.SettingsManager;

import org.bukkit.entity.Player;



public class Spectate implements SubCommand{

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (!player.hasPermission(permission()) && !player.isOp()) {
            MessageManager.getInstance().sendFMessage(MessageManager.PrefixType.ERROR, "error.nopermission", player);
            return true;
        }
        
        if(args.length == 0){
            if(GameManager.getInstance().isSpectator(player)){
                GameManager.getInstance().removeSpectator(player);
                return true;
            }
            else{
                MessageManager.getInstance().sendFMessage(MessageManager.PrefixType.ERROR, "error.notspecified", player, "input-Game ID");
                return true;
            }
        }
        if(SettingsManager.getInstance().getSpawnCount(Integer.parseInt(args[0])) == 0){
            MessageManager.getInstance().sendMessage(MessageManager.PrefixType.ERROR, "error.nospawns", player);
            return true;
        }
        if(GameManager.getInstance().isPlayerActive(player)){
            MessageManager.getInstance().sendMessage(MessageManager.PrefixType.ERROR, "error.specingame", player);
            return true;
        }
        GameManager.getInstance().getGame(Integer.parseInt(args[0])).addSpectator(player);
        return true;
    }

    @Override
    public String help(Player p) {
        return "/tw spectate <id> - " + SettingsManager.getInstance().getMessageConfig().getString("messages.help.spectate", "Spectate a running arena");
    }

	@Override
	public String permission() {
		return "tw.arena.spectate";
	}

}