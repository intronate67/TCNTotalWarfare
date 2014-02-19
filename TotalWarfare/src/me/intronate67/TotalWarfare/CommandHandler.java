package me.intronate67.TotalWarfare;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import me.intronate67.TotalWarfare.MessageManager.PrefixType;
import me.intronate67.TotalWarfare.commands.AddWall;
import me.intronate67.TotalWarfare.commands.CreateArena;
import me.intronate67.TotalWarfare.commands.DelArena;
import me.intronate67.TotalWarfare.commands.Disable;
import me.intronate67.TotalWarfare.commands.Enable;
import me.intronate67.TotalWarfare.commands.Flag;
import me.intronate67.TotalWarfare.commands.ForceStart;
import me.intronate67.TotalWarfare.commands.Join;
import me.intronate67.TotalWarfare.commands.Leave;
import me.intronate67.TotalWarfare.commands.LeaveQueue;
import me.intronate67.TotalWarfare.commands.ListArenas;
import me.intronate67.TotalWarfare.commands.ListPlayers;
import me.intronate67.TotalWarfare.commands.Reload;
import me.intronate67.TotalWarfare.commands.ResetSpawns;
import me.intronate67.TotalWarfare.commands.SetCapture;
import me.intronate67.TotalWarfare.commands.SetLobbySpawn;
import me.intronate67.TotalWarfare.commands.SetLobbyWall;
import me.intronate67.TotalWarfare.commands.SetSpawn;
import me.intronate67.TotalWarfare.commands.Spectate;
import me.intronate67.TotalWarfare.commands.SubCommand;
import me.intronate67.TotalWarfare.commands.Teleport;
import me.intronate67.TotalWarfare.commands.Vote;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;



public class CommandHandler implements CommandExecutor {
	private Plugin plugin;
	private HashMap < String, SubCommand > commands;
	private HashMap < String, Integer > helpinfo;
	private MessageManager msgmgr = MessageManager.getInstance();
	public CommandHandler(Plugin plugin) {
		this.plugin = plugin;
		commands = new HashMap < String, SubCommand > ();
		helpinfo = new HashMap < String, Integer > ();
		loadCommands();
		loadHelpInfo();
	}

	private void loadCommands() {
		commands.put("createarena", new CreateArena());
		commands.put("join", new Join());
		commands.put("addwall", new AddWall());
		commands.put("setspawn", new SetSpawn());
		commands.put("getcount", new ListArenas());
		commands.put("disable", new Disable());
		commands.put("start", new ForceStart());
		commands.put("enable", new Enable());
		commands.put("vote", new Vote());
		commands.put("leave", new Leave());
		commands.put("setlobbyspawn", new SetLobbySpawn());
		commands.put("setlobbywall", new SetLobbyWall());
		commands.put("resetspawns", new ResetSpawns());
		commands.put("delarena", new DelArena());
		commands.put("flag", new Flag());
		commands.put("spectate", new Spectate());
		commands.put("lq", new LeaveQueue());
		commands.put("leavequeue", new LeaveQueue());
		commands.put("list", new ListPlayers());
		commands.put("tp", new Teleport());
		commands.put("reload", new Reload());
		commands.put("setcapture", new SetCapture());
//		commands.put("test", new Test());

		// commands.put("sponsor", new Sponsor());
	}

	private void loadHelpInfo() {
		//you can do this by iterating thru the hashmap from a certian index btw instead of using a new hashmap,
		//plus, instead of doing three differnet ifs, just iterate thru and check if the value == the page
		helpinfo.put("createarena", 3);
		helpinfo.put("join", 1);
		helpinfo.put("addwall", 3);
		helpinfo.put("setspawn", 3);
		helpinfo.put("getcount", 3);
		helpinfo.put("disable", 2);
		helpinfo.put("start", 2);
		helpinfo.put("enable", 2);
		helpinfo.put("vote", 1);
		helpinfo.put("leave", 1);
		helpinfo.put("setlobbyspawn", 3);
		helpinfo.put("resetspawns", 3);
		helpinfo.put("delarena", 3);
		helpinfo.put("flag", 3);
		helpinfo.put("spectate", 1);
		helpinfo.put("lq", 1);
		helpinfo.put("leavequeue", 1);
		helpinfo.put("list", 1);
		helpinfo.put("setcapture", 3);
		commands.put("reload", new Reload());

		//helpinfo.put("sponsor", 1);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd1, String commandLabel, String[] args) {
		PluginDescriptionFile pdfFile = plugin.getDescription();
		if (!(sender instanceof Player)) {
			msgmgr.logMessage(PrefixType.WARNING, "Only in-game players can use TotalWarfare commands! ");
			return true;
		}

		Player player = (Player) sender;

		if (TotalWarfare.config_todate == false) {
			msgmgr.sendMessage(PrefixType.WARNING, "The config file is out of date. Please tell an administrator to reset the config.", player);
			return true;
		}

		if (TotalWarfare.dbcon == false) {
			msgmgr.sendMessage(PrefixType.WARNING, "Could not connect to server. Plugin disabled.", player);
			return true;
		}

		if (cmd1.getName().equalsIgnoreCase("totalwarfare")) {
			if (args == null || args.length < 1) {
				msgmgr.sendMessage(PrefixType.INFO, "Version " + pdfFile.getVersion() + " by intronate67", player);
				msgmgr.sendMessage(PrefixType.INFO, "Type /tw help <player | staff | admin> for command information", player);
				return true;
			}
			if (args[0].equalsIgnoreCase("help")) {
				if (args.length == 1) {
					help(player, 1);
				}
				else {
					if (args[1].toLowerCase().startsWith("player")) {
						help(player, 1);
						return true;
					}
					if (args[1].toLowerCase().startsWith("staff")) {
						help(player, 2);
						return true;
					}
					if (args[1].toLowerCase().startsWith("admin")) {
						help(player, 3);
						return true;
					}
					else {
						msgmgr.sendMessage(PrefixType.WARNING, args[1] + " is not a valid page! Valid pages are Player, Staff, and Admin.", player);
					}
				}
				return true;
			}
			String sub = args[0];
			Vector < String > l = new Vector < String > ();
			l.addAll(Arrays.asList(args));
			l.remove(0);
			args = (String[]) l.toArray(new String[0]);
			if (!commands.containsKey(sub)) {
				msgmgr.sendMessage(PrefixType.WARNING, "Command doesn't exist.", player);
				msgmgr.sendMessage(PrefixType.INFO, "Type /tw help for command information", player);
				return true;
			}
			try {
				commands.get(sub).onCommand(player, args);
			} catch (Exception e) {
				e.printStackTrace();
				msgmgr.sendFMessage(PrefixType.ERROR, "error.command", player, "command-["+sub+"] "+Arrays.toString(args));
				msgmgr.sendMessage(PrefixType.INFO, "Type /tw help for command information", player);
			}
			return true;
		}
		return false;
	}

	public void help (Player p, int page) {
		if (page == 1) {
			p.sendMessage(ChatColor.GRAY + "------------ " + msgmgr.pre + ChatColor.BLUE + "Player Commands" + ChatColor.GRAY + " ------------");
		}
		if (page == 2) {
			p.sendMessage(ChatColor.GRAY + "------------ " + msgmgr.pre + ChatColor.BLUE + "Staff Commands" + ChatColor.GRAY + " ------------");
		}
		if (page == 3) {
			p.sendMessage(ChatColor.GRAY + "------------ " + msgmgr.pre + ChatColor.BLUE + "Admin Commands" + ChatColor.GRAY + " ------------");
		}

		for (String command : commands.keySet()) {
			try{
				if (helpinfo.get(command) == page) {

					msgmgr.sendMessage(PrefixType.INFO, commands.get(command).help(p), p);
				}
			}catch(Exception e){}
		}
	}
}