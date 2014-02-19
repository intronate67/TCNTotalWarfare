package me.intronate67.TotalWarfare.commands;

import me.intronate67.TotalWarfare.LobbyManager;
import me.intronate67.TotalWarfare.MessageManager;
import me.intronate67.TotalWarfare.SettingsManager;

import org.bukkit.entity.Player;



public class AddWall implements SubCommand{

    @Override
    public boolean onCommand(Player player, String[] args) {
        if(!player.hasPermission(permission()) && !player.isOp()){
            MessageManager.getInstance().sendFMessage(MessageManager.PrefixType.ERROR, "error.nopermission", player);
            return true;
        }
        else if(args.length<1){
                MessageManager.getInstance().sendFMessage(MessageManager.PrefixType.ERROR, "error.notspecified", player, "input-Arena");
        	return true;
        }
       LobbyManager.getInstance().setLobbySignsFromSelection(player, Integer.parseInt(args[0]));
       return true;
    }

    @Override
    public String help(Player p) {
        return "/tw addwall <id> - " + SettingsManager.getInstance().getMessageConfig().getString("messages.help.addwall", "Add a lobby stats wall for Arena <id>");
    }

	@Override
	public String permission() {
		return "tw.admin.addwall";
	}

    //TODO: TAKE A W.E SELECTIONA AND SET THE LOBBY. ALSO SET LOBBY WALL
}