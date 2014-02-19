package me.intronate67.TotalWarfare.commands;

import java.util.ArrayList;
import java.util.Random;

import me.intronate67.TotalWarfare.Game.Team;
import me.intronate67.TotalWarfare.GameManager;
import me.intronate67.TotalWarfare.MessageManager;
import me.intronate67.TotalWarfare.MessageManager.PrefixType;
import me.intronate67.TotalWarfare.SettingsManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;


public class Join implements SubCommand{

	public ArrayList<Player> inG(){
		ArrayList<Player> inGame = new ArrayList<Player>();
		
		return inGame;
	}
	
	public ArrayList<Player> rT(){
		ArrayList<Player> redTeam = new ArrayList<Player>();
		
		return redTeam;
	}
	
	public ArrayList<Player> bT(){
		ArrayList<Player> blueTeam = new ArrayList<Player>();
		
		return blueTeam;
	}
	
	public Random rand = new Random();
	
	public boolean onCommand(Player player, String[] args) {
		if(args.length == 1){
			if(player.hasPermission(permission())){
				try {
					World heroWorld = Bukkit.getWorld(SettingsManager.getInstance().getSpawns().getString("spawns." + args[0] + ".heroes" + ".world"));
					int heroX = SettingsManager.getInstance().getSpawns().getInt("spawns." + args[0] + ".heroes" + ".x");
					int heroZ = SettingsManager.getInstance().getSpawns().getInt("spawns." + args[0] + ".heroes" + ".z");
					int heroY = SettingsManager.getInstance().getSpawns().getInt("spawns." + args[0] + ".heroes" + ".y");
					World demonWorld = Bukkit.getWorld(SettingsManager.getInstance().getSpawns().getString("spawns." + args[0] + ".demons" + ".world"));
					int demonX = SettingsManager.getInstance().getSpawns().getInt("spawns." + args[0] + ".demons" + ".x");
					int demonZ = SettingsManager.getInstance().getSpawns().getInt("spawns." + args[0] + ".demons" + ".z");
					int demonY = SettingsManager.getInstance().getSpawns().getInt("spawns." + args[0] + ".demons" + ".y");
					Location demonSpawn = new Location(demonWorld, demonX, demonY, demonZ);
					Location heroSpawn = new Location(heroWorld, heroX, heroY, heroZ);
					int n = rand.nextInt(24) + 1;
					int a = Integer.parseInt(args[0]);
					GameManager.getInstance().addPlayer(player, a);
					if(n <= 12){
						player.teleport(heroSpawn);
						MessageManager.getInstance().broadcastMessage(MessageManager.PrefixType.INFO, "Player: " + player.getName() + " has joined the: " + ChatColor.BLUE + Team.HEROES + ChatColor.GRAY +" team!", player);
						inG().add(player);
					}else{
						player.teleport(demonSpawn);
						MessageManager.getInstance().broadcastMessage(MessageManager.PrefixType.INFO, "Player: " + player.getName() + " has joined the: " + ChatColor.RED + Team.DEMONS + ChatColor.GRAY + " team!", player);
						inG().add(player);
					}
				} catch (NumberFormatException e) {
					MessageManager.getInstance().sendFMessage(PrefixType.ERROR, "error.notanumber", player, "input-" + args[0]);
				}
			}
			else{
				MessageManager.getInstance().sendFMessage(PrefixType.WARNING, "error.nopermission", player);
			}
		}
		else{
			if(player.hasPermission("tw.lobby.join")){
				if(GameManager.getInstance().getPlayerGameId(player)!=-1){
					MessageManager.getInstance().sendMessage(PrefixType.ERROR, "error.alreadyingame", player);
					return true;
				}
				player.teleport(SettingsManager.getInstance().getLobbySpawn());
				return true;
			}
			else{
				MessageManager.getInstance().sendFMessage(PrefixType.WARNING, "error.nopermission", player);
			}
		}
		return true;
	}

	@Override
	public String help(Player p) {
		return "/tw join - " + SettingsManager.getInstance().getMessageConfig().getString("messages.help.join", "Join the lobby");
	}

	@Override
	public String permission() {
		return "tw.arena.join";
	}
}