package me.intronate67.TotalWarfare.events;

import java.util.ArrayList;

import me.intronate67.TotalWarfare.Game;
import me.intronate67.TotalWarfare.GameManager;
import me.intronate67.TotalWarfare.SettingsManager;
import me.intronate67.TotalWarfare.commands.Join;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class DeathEvent implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDieEvent(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player){}
		else 
			return;
		Player player = (Player)event.getEntity();
		int gameid = GameManager.getInstance().getPlayerGameId(player);
		World heroWorld = Bukkit.getWorld(SettingsManager.getInstance().getSpawns().getString("spawns." + gameid + ".heroes" + ".world"));
		int heroX = SettingsManager.getInstance().getSpawns().getInt("spawns." + gameid + ".heroes" + ".x");
		int heroZ = SettingsManager.getInstance().getSpawns().getInt("spawns." + gameid + ".heroes" + ".z");
		int heroY = SettingsManager.getInstance().getSpawns().getInt("spawns." + gameid + ".heroes" + ".y");
		World demonWorld = Bukkit.getWorld(SettingsManager.getInstance().getSpawns().getString("spawns." + gameid + ".demons" + ".world"));
		int demonX = SettingsManager.getInstance().getSpawns().getInt("spawns." + gameid + ".demons" + ".x");
		int demonZ = SettingsManager.getInstance().getSpawns().getInt("spawns." + gameid + ".demons" + ".z");
		int demonY = SettingsManager.getInstance().getSpawns().getInt("spawns." + gameid + ".demons" + ".y");
		Location demonSpawn = new Location(demonWorld, demonX, demonY, demonZ);
		Location heroSpawn = new Location(heroWorld, heroX, heroY, heroZ);
		
		if(gameid==-1)
			return;
		if(!GameManager.getInstance().isPlayerActive(player))
			return;
		Game game = GameManager.getInstance().getGame(gameid);
		if(game.getMode() != Game.GameMode.INGAME){
			event.setCancelled(true);
			return;
		}
		if(game.isProtectionOn()){
			event.setCancelled(true);
			return;
		}
		if(player.getHealth() <= event.getDamage()){
			event.setCancelled(true);
			player.setHealth(player.getMaxHealth());
			player.setFoodLevel(20);
			player.setFireTicks(0);
			PlayerInventory inv = player.getInventory();
			Location l = player.getLocation();

			for(ItemStack i: inv.getContents()){
				if(i!=null)
					l.getWorld().dropItemNaturally(l, i);
			}
			for(ItemStack i: inv.getArmorContents()){
				if(i!=null && i.getTypeId() !=0)
					l.getWorld().dropItemNaturally(l, i);
			}
			ArrayList<Player> redTeam = (new Join()).rT();
			ArrayList<Player> blueTeam = (new Join()).bT();
			if(blueTeam.contains(player)){
				player.teleport(demonSpawn);
			}
			if(redTeam.contains(player)){
				player.teleport(heroSpawn);
			}
		}
	}

}