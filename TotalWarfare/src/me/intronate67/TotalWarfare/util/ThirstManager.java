package me.intronate67.TotalWarfare.util;

import me.intronate67.TotalWarfare.Game;
import me.intronate67.TotalWarfare.GameManager;
import me.intronate67.TotalWarfare.TotalWarfare;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ThirstManager implements Listener {

	ThirstManager instance = new ThirstManager();

	public ThirstManager getInstance() {
		return instance;
	}

	@SuppressWarnings("deprecation")
	public void startThirst() {
		Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(new TotalWarfare(), new Runnable() {
    	public void run() {
    		for (Game g : GameManager.getInstance().getGames()) {
    			for (Player p : g.getAllPlayers()) {
    				removeThirst(p, 1);
    			}
    		}
    	}
    }, 60L, 200L);
	}

	public void removeThirst(Player p, int amount) {
		p.setLevel(p.getLevel() - amount);
	}

	public void addThirst(Player p, int amount) {
		p.setLevel(p.getLevel() + amount);
	}

	public void startThirst(Player p) {
		p.setLevel(30);
	}

	@EventHandler
	public void onPlayerDrinkWater(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getPlayer().getItemInHand() == new ItemStack(Material.POTION)) {
				e.getPlayer().getInventory().removeItem(new ItemStack(Material.POTION, 1));
				addThirst(e.getPlayer(), 5);
				e.getPlayer().sendMessage(ChatColor.GREEN + "You drank water.");
			}
		}
	}
}