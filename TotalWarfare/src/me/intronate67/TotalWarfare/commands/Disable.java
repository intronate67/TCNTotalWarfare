package me.intronate67.TotalWarfare.commands;

import me.intronate67.TotalWarfare.Game;
import me.intronate67.TotalWarfare.GameManager;
import me.intronate67.TotalWarfare.MessageManager;
import me.intronate67.TotalWarfare.SettingsManager;

import org.bukkit.entity.Player;



public class Disable implements SubCommand{

    @Override
    public boolean onCommand(Player player, String[] args) {        
        if(!player.hasPermission(permission()) && !player.isOp()){
            MessageManager.getInstance().sendFMessage(MessageManager.PrefixType.ERROR, "error.nopermission", player);
            return true;
        }
        try{
        if(args.length == 0){
            for(Game g: GameManager.getInstance().getGames()){
                g.disable();
            }
                MessageManager.getInstance().sendFMessage(MessageManager.PrefixType.INFO, "game.all", player, "input-disabled");

        }else{

            GameManager.getInstance().disableGame(Integer.parseInt(args[0]));
                MessageManager.getInstance().sendFMessage(MessageManager.PrefixType.INFO, "game.state", player, "arena-" + args[0], "input-disabled");
        }
        } catch (NumberFormatException e) {
            MessageManager.getInstance().sendFMessage(MessageManager.PrefixType.ERROR, "error.notanumber", player, "input-Arena");
        } catch (NullPointerException e) {
            MessageManager.getInstance().sendFMessage(MessageManager.PrefixType.ERROR, "error.gamedoesntexist", player, "arena-" + args[0]);
        }
        return true;
    }
    @Override
    public String help(Player p) {
        return "/tw disable <id> - " + SettingsManager.getInstance().getMessageConfig().getString("messages.help.disable", "Disables arena <id>");
    }
	@Override
	public String permission() {
		return "tw.arena.disable";
	}
}