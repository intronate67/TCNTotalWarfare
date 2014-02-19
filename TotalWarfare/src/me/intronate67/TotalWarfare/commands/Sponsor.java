package me.intronate67.TotalWarfare.commands;

import me.intronate67.TotalWarfare.MessageManager;

import org.bukkit.entity.Player;



public class Sponsor implements SubCommand {

	MessageManager msgmgr = MessageManager.getInstance();

	@Override
	public boolean onCommand(Player player, String[] args) {
		return true;
	}

	@Override
	public String help(Player p) {
		return "/ng sponsor <player> <itemid> <amount> - Sponsor a player!";
	}

	@Override
	public String permission() {
		return "sg.player.sponsor";
	}
}