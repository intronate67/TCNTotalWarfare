package me.intronate67.TotalWarfare.events;

import me.intronate67.TotalWarfare.Game;
import me.intronate67.TotalWarfare.GameManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;



public class LogoutEvent implements Listener{

    
    @EventHandler
    public void PlayerLoggout(PlayerQuitEvent e){
        Player p = e.getPlayer();
        GameManager.getInstance().removeFromOtherQueues(p, -1);
        int id = GameManager.getInstance().getPlayerGameId(p);
        if(GameManager.getInstance().isSpectator(p))
        	GameManager.getInstance().removeSpectator(p);
        if(id == -1) return;
        if(GameManager.getInstance().getGameMode(id)==Game.GameMode.INGAME)
            GameManager.getInstance().getGame(id).killPlayer(p, true);
        else
            GameManager.getInstance().getGame(id).removePlayer(p, true);
        
        //GameManager.getInstance().removePlayerRefrence(p);
    }
    
}