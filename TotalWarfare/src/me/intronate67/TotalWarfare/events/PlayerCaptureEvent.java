package me.intronate67.TotalWarfare.events;

import me.intronate67.TotalWarfare.GameManager;
import me.intronate67.TotalWarfare.SettingsManager;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerCaptureEvent {

	public void playerCaptureHandler(PlayerMoveEvent e){
		Player p = e.getPlayer();
		if(GameManager.getInstance().getPlayerGameId(e.getPlayer()) == -1){
            return;
        }else{
        	if(p.getLocation().getX() == SettingsManager.getInstance().getSpawns().getDouble("spawns." + GameManager.getInstance().getPlayerGameId(e.getPlayer()) + ".capture" + ".x1") 
        			&& p.getLocation().getZ() == SettingsManager.getInstance().getSpawns().getDouble("spawns." + GameManager.getInstance().getPlayerGameId(e.getPlayer()) + ".capture" + ".z1")
        			&& p.getLocation().getY() == SettingsManager.getInstance().getSpawns().getDouble("spawns." + GameManager.getInstance().getPlayerGameId(e.getPlayer()) + ".capture" + ".y1")
        			|| p.getLocation().getX() == SettingsManager.getInstance().getSpawns().getDouble("spawns." + GameManager.getInstance().getPlayerGameId(e.getPlayer()) + ".capture" + ".x2") 
                	&& p.getLocation().getZ() == SettingsManager.getInstance().getSpawns().getDouble("spawns." + GameManager.getInstance().getPlayerGameId(e.getPlayer()) + ".capture" + ".z2")
                	&& p.getLocation().getY() == SettingsManager.getInstance().getSpawns().getDouble("spawns." + GameManager.getInstance().getPlayerGameId(e.getPlayer()) + ".capture" + ".y2")){
        		
        	}
        }
	}
	
}
