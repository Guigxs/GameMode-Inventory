package org.guigxs.quickinventory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class ConfigManager {
	private QuickInventory plugin = QuickInventory.getPlugin(QuickInventory.class);
	
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
			
			inventoryConfig.set("Id." + player.getUniqueId().toString() + "." + player.getGameMode().toString() + ".xp",
					player.getExp());
			inventoryConfig.set("Id." + player.getUniqueId().toString() + "." + player.getGameMode().toString() + ".health",
					player.getHealth());
			inventoryConfig.set("Id." + player.getUniqueId().toString() + "." + player.getGameMode().toString() + ".food-level",
					player.getFoodLevel());
			inventoryConfig.set("Id." + player.getUniqueId().toString() + "." + player.getGameMode().toString() + ".inventory", 
					Arrays.asList(player.getInventory().getContents()));
			
			inventoryConfig.save(inventoryFile);
			//Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "inventoris.yml has been saved");
			return true;
			
		} catch(IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "Could not save the inventoris.yml file");
			return false;
		}
	}
	
	public void reloadInventory() {
		inventoryConfig = YamlConfiguration.loadConfiguration(inventoryFile);
		//Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "inventoris.yml has been reloaded");
	}
	
	public boolean cleanInventory(Player player, GameMode gamemode) {
		try {
			inventoryConfig.set("Id." + player.getUniqueId().toString() + "." + gamemode.toString(), null);
			inventoryConfig.save(inventoryFile);
			return true;
			
		} catch (IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "Could not save the inventoris.yml file");
			return false;
		}
	}

	
	public HashMap<String, Object> loadInventory(Player player, GameMode gamemode) {
		inventoryConfig = YamlConfiguration.loadConfiguration(inventoryFile);
		
		ItemStack[] is;
		HashMap<String, Object> playerInfos = new HashMap<String, Object>();
		
		try {

			float xp = (float) inventoryConfig.get("Id." + player.getUniqueId().toString() + "." + gamemode.toString() + ".xp");
			playerInfos.put("xp", xp);
			
			Double health = inventoryConfig.getDouble("Id." + player.getUniqueId().toString() + "." + gamemode.toString() + ".health");
			playerInfos.put("health", health);
			
			int foodlvl = inventoryConfig.getInt("Id." + player.getUniqueId().toString() + "." + gamemode.toString() + ".food-level");
			playerInfos.put("food-level", foodlvl);
			
			//List<ItemStack> list = (List<ItemStack>) inventoryConfig.get("Id." + player.getUniqueId().toString() + "." + gamemode.toString() + ".inventory");
			@SuppressWarnings("unchecked")
			List<ItemStack> list = (List<ItemStack>) inventoryConfig.getList("Id." + player.getUniqueId().toString() + "." + gamemode.toString() + ".inventory");
			is = new ItemStack[list.size()];
			list.toArray(is);
			
			//Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + player.getName() +"' inventory ["+ gamemode.toString() +"] has been loaded");

		} catch(Exception e){
			playerInfos.put("xp", 0.0f);
			playerInfos.put("health", 20.0d);
			playerInfos.put("food-level", 20);
			is = new ItemStack[0];	
			//Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Can't get " + player.getName() + "'s inventory [" + gamemode.toString() + "], creating...");
			
		}
		
		playerInfos.put("inventory", is);
		return playerInfos;

	}
	
}
