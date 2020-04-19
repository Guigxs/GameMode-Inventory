package org.guigxs.quickInv;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class ConfigManager {
	private QuickInv plugin = QuickInv.getPlugin(QuickInv.class);
	
	public FileConfiguration inventoryConfig;
	public File inventoryFile;
	
	public ConfigManager() {
		
		if(!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}
		
		inventoryFile = new File(plugin.getDataFolder(), "inventories.yml");
		
		if(!inventoryFile.exists()) {
			try {
				inventoryFile.createNewFile();
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "inventoris.yml has been created!");
			}catch(IOException e){
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "Could not create inventoris.yml");
			}
		}
		
		inventoryConfig = YamlConfiguration.loadConfiguration(inventoryFile);
	}
	
	public FileConfiguration getInventoryConfig() {
		return inventoryConfig;
	}
	
	
	public boolean saveInventory(Player player) {
		try {
			inventoryConfig.set("Id." + player.getUniqueId().toString() + "." + player.getGameMode().toString(), 
					Arrays.asList(player.getInventory().getContents()));
			inventoryConfig.save(inventoryFile);
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "inventoris.yml has been saved");
			return true;
			
		} catch(IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "Could not save the inventoris.yml file");
			return false;
		}
	}
	
	public void reloadInventory() {
		inventoryConfig = YamlConfiguration.loadConfiguration(inventoryFile);
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "inventoris.yml has been reloaded");
	}

	
	public ItemStack[] loadInventory(Player player, GameMode gamemode) {
		inventoryConfig = YamlConfiguration.loadConfiguration(inventoryFile);
		
		ItemStack[] is;
		
		try {
			@SuppressWarnings("unchecked")
			List<ItemStack> list = (List<ItemStack>) inventoryConfig.get("Id." + player.getUniqueId().toString() + "." + gamemode.toString());
			is = new ItemStack[list.size()];
			list.toArray(is);
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + player.getName() +"' inventory ["+ gamemode.toString() +"] has been loaded");

		} catch(Exception e){
			is = new ItemStack[0];	
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Can't get " + player.getName() + "'s inventory [" + gamemode.toString() + "]");
			
		}

		return is;

	}
	
}
