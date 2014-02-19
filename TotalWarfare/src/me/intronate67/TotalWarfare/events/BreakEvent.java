package me.intronate67.TotalWarfare.events;

import java.util.ArrayList;

import me.intronate67.TotalWarfare.Game;
import me.intronate67.TotalWarfare.GameManager;
import me.intronate67.TotalWarfare.SettingsManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;



public class BreakEvent implements Listener {

    public ArrayList<Integer> allowedBreak =  new ArrayList<Integer>();;

    public BreakEvent(){
        allowedBreak.addAll( SettingsManager.getInstance().getConfig().getIntegerList("block.break.whitelist"));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        int pid = GameManager.getInstance().getPlayerGameId(p);


        if(pid == -1){
            int blockgameid  = GameManager.getInstance().getBlockGameId(event.getBlock().getLocation());

            if(blockgameid != -1){
                if(GameManager.getInstance().getGame(blockgameid).getGameMode() != Game.GameMode.DISABLED){
                    event.setCancelled(true);
                }
            }
            return;
        }


        Game g = GameManager.getInstance().getGame(pid);

        if(g.getMode() == Game.GameMode.DISABLED){
            return;
        }
        if(g.getMode() != Game.GameMode.INGAME){
            event.setCancelled(true);
            return;
        }

        if(!allowedBreak.contains(event.getBlock().getTypeId()))event.setCancelled(true);
    }
}