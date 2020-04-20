package org.guigxs.quickinventory.utils;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.guigxs.quickinventory.ConfigManager;
import org.guigxs.quickinventory.QuickInventory;

public class CustomInventory implements Listener{
	
	Plugin plugin = QuickInventory.getPlugin(QuickInventory.class);
	ConfigManager configManager =  new ConfigManager();
	
	public Inventory newInventory(Player targetPlayer, GameMode gamemode) {
		
		Inventory inventory = plugin.getServer().createInventory(null, 45, targetPlayer.getName() + "'s inventory [" + gamemode.toString() +"]");
		inventory.setContents(configManager.loadInventory(targetPlayer, gamemode));
		
		return inventory;
	}
	
}
