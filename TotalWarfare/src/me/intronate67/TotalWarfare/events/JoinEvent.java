package me.intronate67.TotalWarfare.events;

import me.intronate67.TotalWarfare.GameManager;
import me.intronate67.TotalWarfare.SettingsManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;



public class JoinEvent implements Listener {
    
    Plugin plugin;
    
    public JoinEvent(Plugin plugin){
        this.plugin = plugin;
    }
    
	@EventHandler
    public void PlayerJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        
        
        if(GameManager.getInstance().getBlockGameId(p.getLocation()) != -1){
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
                public void run(){
                    p.teleport(SettingsManager.getInstance().getLobbySpawn());

                }
            }, 5L);
        }
    }
    
}
