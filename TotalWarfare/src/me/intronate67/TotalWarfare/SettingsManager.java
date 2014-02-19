package me.intronate67.TotalWarfare;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class SettingsManager {
	
	private static SettingsManager instance = new SettingsManager();
	private static Plugin p;
	private FileConfiguration spawns;
	private FileConfiguration system;
	private FileConfiguration kits;
	private FileConfiguration messages;
	private FileConfiguration chest;


	private File f; //spawns
	private File f2; //system
	private File f3; //kits
	private File f4; //messages
	private File f5; //chest

	private static final int KIT_VERSION = 1;
	private static final int MESSAGE_VERSION = 1;
	private static final int CHEST_VERSION = 0;
	private static final int SPAWN_VERSION = 0;
	private static final int SYSTEM_VERSION = 0;


	private SettingsManager() {

	}

	public static SettingsManager getInstance() {
		return instance;
	}

	public void setup(Plugin p) {
		SettingsManager.p = p;
		if (p.getConfig().getInt("config-version") == TotalWarfare.config_version) {
			TotalWarfare.config_todate = true;
		}else{
			File config = new File(p.getDataFolder(), "config.yml");
			config.delete();
		}

		p.getConfig().options().copyDefaults(true);
		p.saveDefaultConfig();

		f = new File(p.getDataFolder(), "spawns.yml");
		f2 = new File(p.getDataFolder(), "system.yml");
		f3 = new File(p.getDataFolder(), "kits.yml");
		f4 = new File(p.getDataFolder(), "messages.yml");
		f5 = new File(p.getDataFolder(), "chest.yml");

		try {
			if (!f.exists()) 	f.createNewFile();
			if (!f2.exists())	f2.createNewFile();
			if (!f3.exists()) 	loadFile("kits.yml");
			if (!f4.exists()) 	loadFile("messages.yml");
			if (!f5.exists()) 	loadFile("chest.yml");

		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		reloadSystem();
		saveSystemConfig();

		reloadSpawns();
		saveSpawns();

		reloadKits();
		//saveKits();

		reloadChest();

		reloadMessages();
		saveMessages();


	}

	public void set(String arg0, Object arg1) {
		p.getConfig().set(arg0, arg1);
	}

	public FileConfiguration getConfig() {
		return p.getConfig();
	}

	public FileConfiguration getSystemConfig() {
		return system;
	}

	public FileConfiguration getSpawns() {
		return spawns;
	}

	public FileConfiguration getKits() {
		return kits;
	}

	public FileConfiguration getChest() {
		return chest;
	}

	public FileConfiguration getMessageConfig() {
		//System.out.println("asdf"+messages.getString("prefix.main"));
		return messages;
	}


	public void saveConfig() {
		// p.saveConfig();
	}

	public static World getGameWorld(int gameID) {
		if (SettingsManager.getInstance().getSystemConfig().getString("tw-system.arenas." + gameID + ".world") == null) {
			//LobbyManager.getInstance().error(true);
			return null;

		}
		return p.getServer().getWorld(SettingsManager.getInstance().getSystemConfig().getString("tw-system.arenas." + gameID + ".world"));
	}

	public void reloadConfig(){
		p.reloadConfig();
	}

	public boolean moveFile(File ff){
		TotalWarfare.$("Moving outdated config file. "+f.getName());
		String name = ff.getName();
		File ff2 = new File(TotalWarfare.getPluginDataFolder(), getNextName(name, 0));
		return ff.renameTo(ff2);
	}

	public String getNextName(String name, int n){
		File ff = new File(TotalWarfare.getPluginDataFolder(), name+".old"+n);
		if(!ff.exists()){
			return ff.getName();
		}
		else{
			return getNextName(name, n+1);
		}
	}


	public void reloadSpawns() {
		spawns = YamlConfiguration.loadConfiguration(f);
		if(spawns.getInt("version", 0) != SPAWN_VERSION){
			moveFile(f);
			reloadSpawns();
		}
		spawns.set("version", SPAWN_VERSION);
		saveSpawns();
	}

	public void reloadSystem() {
		system = YamlConfiguration.loadConfiguration(f2);
		if(system.getInt("version", 0) != SYSTEM_VERSION){
			moveFile(f2);
			reloadSystem();
		}
		system.set("version", SYSTEM_VERSION);
		saveSystemConfig();
	}

	public void reloadKits() {
		kits = YamlConfiguration.loadConfiguration(f3);
		if(kits.getInt("version", 0) != KIT_VERSION){
			moveFile(f3);
			loadFile("kits.yml");
			reloadKits();
		}

	}


	public void reloadMessages() {
		messages = YamlConfiguration.loadConfiguration(f4);
		if(messages.getInt("version", 0) != MESSAGE_VERSION){
			moveFile(f4);
			loadFile("messages.yml");
			reloadKits();
		}
		messages.set("version", MESSAGE_VERSION);
		saveMessages();
	}

	public void reloadChest() {
		chest = YamlConfiguration.loadConfiguration(f5);
		if(chest.getInt("version", 0) != CHEST_VERSION){
			moveFile(f5);
			loadFile("chest.yml");
			reloadKits();
		}
	}





	public void saveSystemConfig() {
		try {
			system.save(f2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveSpawns() {
		try {
			spawns.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveKits() {
		try {
			kits.save(f3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveMessages() {
		try {
			messages.save(f4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveChest() {
		try {
			chest.save(f5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getSpawnCount(int gameID) {
		return spawns.getInt("spawns." + gameID + ".count");
	}


	//TODO: Implement per-arena settings aka flags
	public HashMap < String, Object > getGameFlags(int gameID) {
		HashMap < String, Object > flags = new HashMap < String, Object > ();

		flags.put("AUTOSTART_PLAYERS", system.getInt("tw-system.arenas." + gameID + ".flags.autostart"));
		flags.put("AUTOSTART_VOTE", system.getInt("tw-system.arenas." + gameID + ".flags.vote"));
		flags.put("ENDGAME_ENABLED", system.getBoolean("tw-system.arenas." + gameID + ".flags.endgame-enabled"));
		flags.put("ENDGAME_PLAYERS", system.getInt("tw-system.arenas." + gameID + ".flags.endgame-players"));
		flags.put("ENDGAME_CHEST", system.getBoolean("tw-system.arenas." + gameID + ".flags.endgame-chest"));
		flags.put("ENDGAME_LIGHTNING", system.getBoolean("tw-system.arenas." + gameID + ".flags.endgame-lightning"));
		flags.put("DUEL_PLAYERS", system.getInt("tw-system.arenas." + gameID + ".flags.endgame-duel-players"));
		flags.put("DUEL_TIME", system.getInt("tw-system.arenas." + gameID + ".flags.endgame-duel-time"));
		flags.put("DUEL_ENABLED", system.getBoolean("tw-system.arenas." + gameID + ".flags.endgame-duel"));
		flags.put("ARENA_NAME", system.getString("tw-system.arenas." + gameID + ".flags.arena-name"));
		flags.put("ARENA_COST", system.getInt("tw-system.arenas." + gameID + ".flags.arena-cost"));
		flags.put("ARENA_REWARD", system.getInt("tw-system.arenas." + gameID + ".flags.arena-reward"));
		flags.put("ARENA_MAXTIME", system.getInt("tw-system.arenas." + gameID + ".flags.arena-maxtime"));
		flags.put("SPONSOR_ENABLED", system.getBoolean("tw-system.arenas." + gameID + ".flags.sponsor-enabled"));
		flags.put("SPONSOR_MODE", system.getInt("tw-system.arenas." + gameID + ".flags.sponsor-mode"));

		return flags;

	}
	public void saveGameFlags(HashMap < String, Object > flags, int gameID) {

		system.set("tw-system.arenas." + gameID + ".flags.autostart", flags.get("AUTOSTART_PLAYERS"));
		system.set("tw-system.arenas." + gameID + ".flags.vote", flags.get("AUTOSTART_VOTE"));
		system.set("tw-system.arenas." + gameID + ".flags.endgame-enabled", flags.get("ENDGAME_ENABLED"));
		system.set("tw-system.arenas." + gameID + ".flags.endgame-players", flags.get("ENDGAME_PLAYERS"));
		system.set("tw-system.arenas." + gameID + ".flags.endgame-chest", flags.get("ENDGAME_CHEST"));
		system.set("tw-system.arenas." + gameID + ".flags.endgame-lightning", flags.get("ENDGAME_LIGHTNING"));
		system.set("tw-system.arenas." + gameID + ".flags.endgame-duel-players", flags.get("DUEL_PLAYERS"));
		system.set("tw-system.arenas." + gameID + ".flags.endgame-duel-time", flags.get("DUEL_TIME"));
		system.set("tw-system.arenas." + gameID + ".flags.endgame-duel", flags.get("DUEL_ENABLED"));
		system.set("tw-system.arenas." + gameID + ".flags.arena-name", flags.get("ARENA_NAME"));
		system.set("tw-system.arenas." + gameID + ".flags.arena-cost", flags.get("ARENA_COST"));
		system.set("tw-system.arenas." + gameID + ".flags.arena-reward", flags.get("ARENA_REWARD"));
		system.set("tw-system.arenas." + gameID + ".flags.arena-maxtime", flags.get("ARENA_MAXTIME"));
		system.set("tw-system.arenas." + gameID + ".flags.sponsor-enabled", flags.get("SPONSOR_ENABLED"));
		system.set("tw-system.arenas." + gameID + ".flags.sponsor-mode", flags.get("SPONSOR_MODE"));

		saveSystemConfig();

	}

	public Location getLobbySpawn() {
		try{
			return new Location(Bukkit.getWorld(system.getString("tw-system.lobby.spawn.world")),
				system.getInt("tw-system.lobby.spawn.x"),
				system.getInt("tw-system.lobby.spawn.y"),
				system.getInt("tw-system.lobby.spawn.z"),
				system.getInt("tw-system.lobby.spawn.yaw"),
				system.getInt("tw-system.lobby.spawn.pitch"));
		}catch(Exception e){
			return null;
		}
	}

	public Location getSpawnPoint(int gameid, int a) {
		return new Location(getGameWorld(gameid),
				spawns.getInt("spawns." + gameid + "." + a + ".x"),
				spawns.getInt("spawns." + gameid + "." + a + ".y"),
				spawns.getInt("spawns." + gameid + "." + a + ".z"),
				(float)spawns.getDouble("spawns." + gameid + "." + a + ".yaw"),
				(float)spawns.getDouble("spawns." + gameid + "." + a + ".pitch"));
	}

	public void setLobbySpawn(Location l) {
		system.set("tw-system.lobby.spawn.world", l.getWorld().getName());
		system.set("tw-system.lobby.spawn.x", l.getBlockX());
		system.set("tw-system.lobby.spawn.y", l.getBlockY());
		system.set("tw-system.lobby.spawn.z", l.getBlockZ());
		system.set("tw-system.lobby.spawn.yaw", l.getYaw());
		system.set("tw-system.lobby.spawn.pitch", l.getPitch());
	}


	public void setSpawn(int gameid, String teamSpawn, Location v) {
		
		if(teamSpawn.equalsIgnoreCase("demons")){
			spawns.set("spawns." + gameid + "." + "demons" + ".world", v.getWorld().getName());
			spawns.set("spawns." + gameid + "." + "demons" + ".x", v.getBlockX());
			spawns.set("spawns." + gameid + "." + "demons" + ".y", v.getBlockY());
			spawns.set("spawns." + gameid + "." + "demons" + ".z", v.getBlockZ());
			spawns.set("spawns." + gameid + "." + "demons" + ".yaw", v.getYaw());
			spawns.set("spawns." + gameid + "." + "demons" + ".pitch", v.getPitch());
			
		}
		if(teamSpawn.contains("heroes")){
			spawns.set("spawns." + gameid + "." + "heroes" + ".world", v.getWorld().getName());
			spawns.set("spawns." + gameid + "." + "heroes" + ".x", v.getBlockX());
			spawns.set("spawns." + gameid + "." + "heroes" + ".y", v.getBlockY());
			spawns.set("spawns." + gameid + "." + "heroes" + ".z", v.getBlockZ());
			spawns.set("spawns." + gameid + "." + "heroes" + ".yaw", v.getYaw());
			spawns.set("spawns." + gameid + "." + "heroes" + ".pitch", v.getPitch());
			
		}
		
		try {
			spawns.save(f);
		} catch (IOException e) {

		}
		GameManager.getInstance().getGame(gameid).addSpawn();

	}

	public static String getSqlPrefix() {

		return getInstance().getConfig().getString("sql.prefix");
	}


	public void loadFile(String file){
		File t = new File(p.getDataFolder(), file);
		System.out.println("Writing new file: "+ t.getAbsolutePath());

			try {
				t.createNewFile();
				FileWriter out = new FileWriter(t);
				System.out.println(file);
				InputStream is = getClass().getResourceAsStream("/"+file);
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String line;
				while ((line = br.readLine()) != null) {
					out.write(line+"\n");
					System.out.println(line);
				}
				out.flush();
				is.close();
				isr.close();
				br.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

	}
}