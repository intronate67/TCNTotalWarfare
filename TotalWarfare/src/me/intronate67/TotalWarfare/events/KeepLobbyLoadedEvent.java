package me.intronate67.TotalWarfare.events;

import me.intronate67.TotalWarfare.LobbyManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;



public class KeepLobbyLoadedEvent implements Listener{
    
    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent e){
        LobbyManager.getInstance();
		if(LobbyManager.lobbychunks.contains(e.getChunk())){
            e.setCancelled(true);
        }
        //System.out.println("Chunk unloading");
    }

}