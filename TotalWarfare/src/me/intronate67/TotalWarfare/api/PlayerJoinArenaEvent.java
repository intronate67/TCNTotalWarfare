package me.intronate67.TotalWarfare.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.intronate67.TotalWarfare.Game;



public class PlayerJoinArenaEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Game game;
    private boolean cancelled = false;

    public PlayerJoinArenaEvent(Player p, Game g) {
        player = p;
        game = g;
    }

    public Player getPlayer() {
        return player;
    }
    
    public Game getGame() {
    	return game;
    }
 
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		cancelled = arg0;
	}
}