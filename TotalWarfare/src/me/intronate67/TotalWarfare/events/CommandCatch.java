package me.intronate67.TotalWarfare.events;

import me.intronate67.TotalWarfare.GameManager;
import me.intronate67.TotalWarfare.SettingsManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;



public class CommandCatch implements Listener{

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        String m = event.getMessage();

        if(!GameManager.getInstance().isPlayerActive(event.getPlayer()) && !GameManager.getInstance().isPlayerInactive(event.getPlayer()) && !GameManager.getInstance().isSpectator(event.getPlayer()))
            return;
        if(m.equalsIgnoreCase("/list")){
            event.getPlayer().sendMessage(
            		GameManager.getInstance().getStringList(
            				GameManager.getInstance().getPlayerGameId(event.getPlayer())));
            return;
        }
        if(!SettingsManager.getInstance().getConfig().getBoolean("disallow-commands"))
            return;
        if(event.getPlayer().isOp() || event.getPlayer().hasPermission("sg.staff.nocmdblock"))
            return;
        else if(m.startsWith("/tw") || m.startsWith("/totalwarfare") || m.startsWith("/msg")){
            return;
        }
        else if (SettingsManager.getInstance().getConfig().getStringList("cmdwhitelist").contains(m)) {
        	return;
        }
        event.setCancelled(true);
    }
}
