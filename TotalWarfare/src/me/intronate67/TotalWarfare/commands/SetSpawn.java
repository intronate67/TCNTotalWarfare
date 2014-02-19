package me.intronate67.TotalWarfare.commands;

import java.util.HashMap;

import me.intronate67.TotalWarfare.Game;
import me.intronate67.TotalWarfare.GameManager;
import me.intronate67.TotalWarfare.MessageManager;
import me.intronate67.TotalWarfare.SettingsManager;

import org.bukkit.Location;
import org.bukkit.entity.Player;



public class SetSpawn implements SubCommand{

    HashMap<Integer, Integer>next = new HashMap<Integer,Integer>();

    public enum TeamSpawn{
    	DEMON, HEROES
    }
    public SetSpawn() {
    	
    }

    public void loadNextSpawn(){
        for(Game g:GameManager.getInstance().getGames().toArray(new Game[0])){ //Avoid Concurrency problems
            next.put(g.getID(), SettingsManager.getInstance().getSpawnCount(g.getID())+1);
        }
    }
    
    public boolean onCommand(Player player, String[] args) {
    	
    	
    	if (!player.hasPermission(permission()) && !player.isOp()) {
            MessageManager.getInstance().sendFMessage(MessageManager.PrefixType.ERROR, "error.nopermission", player);
            return true;
        }
        
    	String teamSpawn = args[0];
    	
        loadNextSpawn();
        System.out.println("settings spawn");
        Location l = player.getLocation();
        int game = GameManager.getInstance().getBlockGameId(l);
        System.out.println(game+" "+next.size());
        if(args[0].equalsIgnoreCase("demons") == false && args[0].equalsIgnoreCase("heroes") == false){
        	MessageManager.getInstance().sendMessage(MessageManager.PrefixType.WARNING, "Team: " + args[0] + " does not exist!", player);
        }
        SettingsManager.getInstance().setSpawn(game, teamSpawn, l);
        
        MessageManager.getInstance().sendFMessage(MessageManager.PrefixType.INFO, "info.spawnset", player, "num-" + teamSpawn, "arena-" + game);
        return true;
    }
    
    @Override
    public String help(Player p) {
        return "/tw setspawn next - " + SettingsManager.getInstance().getMessageConfig().getString("messages.help.setspawn", "Sets a spawn for the arena you are located in");
    }

	@Override
	public String permission() {
		return "tw.admin.setarenaspawns";
	}
}