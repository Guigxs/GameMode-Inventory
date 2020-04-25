package org.guigxs.quickinventory.utils;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class EventListener implements Listener{
	
	@EventHandler
	public void onInventoryClickItem(InventoryClickEvent event) {
		
		if (event.getInventory().getSize() == 45 && event.getView().getTitle().contains("'s inventory [")) {
			event.setCancelled(true);
			event.getWhoClicked().sendMessage(ChatColor.YELLOW + "You can't modify this inventory !");
		}
		
		
	}

}
