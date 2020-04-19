package org.guigxs.quickInv;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class PlayerInventory implements Listener{
	
	Plugin plugin = QuickInv.getPlugin(QuickInv.class);
	ConfigManager configManager =  new ConfigManager();
	
	public void newInventory(Player player, Player targetPlayer, GameMode gamemode) {
		
		Inventory inventory = plugin.getServer().createInventory(null, 45, targetPlayer.getName() + "'s inventory [" + gamemode.toString() +"]");
		inventory.setContents(configManager.loadInventory(targetPlayer, gamemode));
		player.openInventory(inventory);
	}
}
