package org.guigxs.quickInv.utils;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import org.guigxs.quickInv.ConfigManager;
import org.guigxs.quickInv.QuickInv;

public class CustomInventory implements Listener{
	
	Plugin plugin = QuickInv.getPlugin(QuickInv.class);
	ConfigManager configManager =  new ConfigManager();
	
	public Inventory newInventory(Player targetPlayer, GameMode gamemode) {
		
		Inventory inventory = plugin.getServer().createInventory(null, 45, targetPlayer.getName() + "'s inventory [" + gamemode.toString() +"]");
		inventory.setContents(configManager.loadInventory(targetPlayer, gamemode));
		
		return inventory;
	}
	
}
